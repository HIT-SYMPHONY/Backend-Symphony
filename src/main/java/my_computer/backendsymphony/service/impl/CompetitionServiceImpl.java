package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.CompetitionFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.NotificationFilterRequest;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.*;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.Notification;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CompetitionMapper;
import my_computer.backendsymphony.domain.mapper.NotificationMapper;
import my_computer.backendsymphony.exception.ForbiddenException;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionUserRepository;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.*;
import my_computer.backendsymphony.service.specification.CompetitionSpecification;
import my_computer.backendsymphony.service.specification.NotificationSpecification;
import my_computer.backendsymphony.util.PaginationUtil;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {
    CompetitionRepository competitionRepository;
    CompetitionMapper competitionMapper;
    UploadFileUtil uploadFileUtil;
    UserService userService;
    UserRepository userRepository;
    AuthorizationService authorizationService;
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    WebSocketNotificationService webSocketNotificationService;
    CompetitionUserRepository competitionUserRepository;
    CompetitionUserService competitionUserService;

    @Override
    @Transactional
    public CompetitionDetailResponse createCompetition(CompetitionRequest request, MultipartFile imageFile) {

        User user = userRepository.findById(request.getCompetitionLeaderId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID
                , new String[]{request.getCompetitionLeaderId()}));
        if (request.getStartTime().isAfter(request.getEndTime()))
            throw new InvalidException(ErrorMessage.Competition.START_TIME_MUST_BEFORE_END_TIME);
        Competition competition = competitionMapper.toCompetition(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            competition.setImage(imageUrl);
        }
        Competition savedCompetition = competitionRepository.save(competition);
        return competitionMapper.toCompetitionDetailResponse(savedCompetition);
    }

    @Override
    @Transactional
    public CompetitionDetailResponse updateCompetition(String id, CompetitionRequest request, MultipartFile imageFile) {
        Competition competition = findCompetitionByIdOrElseThrow(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authorizationService.isCompetitionLeaderOrAdmin(competition, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getStartTime().isAfter(request.getEndTime()))
                throw new InvalidException(ErrorMessage.Competition.START_TIME_MUST_BEFORE_END_TIME);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            competition.setImage(imageUrl);
        }
        competitionMapper.updateCompetition(request, competition);
        competitionRepository.save(competition);
        return competitionMapper.toCompetitionDetailResponse(competition);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<CompetitionSummaryResponse> getAllCompetitions(CompetitionFilterRequest request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.COMPETITION);
        Specification<Competition> spec = Specification.where(
                CompetitionSpecification.hasStatus(request.getStatus())
        );
        spec = spec.and(CompetitionSpecification.hasStartYear(request.getStartYear()));
        spec = spec.and(CompetitionSpecification.matchesKeyword(request.getKeyword()));
        Page<Competition> competitionPage = competitionRepository.findAll(spec, pageable);

        Set<String> registeredIds = new HashSet<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            String currentUserId = authentication.getName();
            List<String> compIds = competitionPage.getContent().stream().map(Competition::getId).collect(Collectors.toList());
            registeredIds = competitionUserRepository.findByUser_IdAndCompetition_IdIn(currentUserId, compIds).stream()
                    .map(cu -> cu.getCompetition().getId())
                    .collect(Collectors.toSet());
        }

        List<CompetitionSummaryResponse> dtos = competitionMapper.toCompetitionSummaryResponseList(competitionPage.getContent(), registeredIds);

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.COMPETITION, competitionPage);

        return new PaginationResponseDto<>(meta, dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetitionResponse getCompetitionById(String id) {
        Competition competition = findCompetitionByIdOrElseThrow(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isRegister = competitionUserService.isUserParticipating(authentication.getName(), id);
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userRole = jwt.getClaimAsString("scope");
        boolean isUser = userRole.equals(Role.USER.name());
        // a user who is not register to the competition
        if (isUser && !isRegister)
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        boolean isAdmin = userRole.equals(Role.ADMIN.name());
        LocalDateTime now = LocalDateTime.now();
        boolean isOngoing = now.isAfter(competition.getStartTime()) && now.isBefore(competition.getEndTime());
        if (!isAdmin && !isOngoing) {
            return competitionMapper.toCompetitionResponse(competition);
        }
        return competitionMapper.toCompetitionDetailResponse(competition);
    }

    @Override
    @Transactional
    public void deleteCompetition(String id) {
        Competition competition = findCompetitionByIdOrElseThrow(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authorizationService.isCompetitionLeaderOrAdmin(competition, authentication))
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        competitionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NotificationResponse createNotification(String competitionId, NotificationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Competition linkedCompetition = findCompetitionByIdOrElseThrow(competitionId);

        if (!authorizationService.isCreatorOrAdmin(linkedCompetition, authentication)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Notification notification = notificationMapper.toNotification(request);
        notification.setCompetition(linkedCompetition);
        Notification savedNotification = notificationRepository.save(notification);

        NotificationResponse response = notificationMapper.toNotificationResponse(savedNotification);
        
        // Populate creator name for the real-time message
        userRepository.findById(savedNotification.getCreatedBy()).ifPresent(creator -> {
            response.setCreatedByName(creator.getFullName());
        });
        
        // Broadcast to topic
        webSocketNotificationService.sendNotificationToCompetition(competitionId, response);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getNotificationsOfCompetition(String id, NotificationFilterRequest request) {
        if (!competitionRepository.existsById(id)) {
            throw new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{id});
        }
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.NOTIFICATION);
        
        Specification<Notification> spec = Specification.where(NotificationSpecification.hasCompetitionId(id));
        spec = spec.and(NotificationSpecification.matchesKeyword(request.getKeyword()));
        
        Page<Notification> notificationPage = notificationRepository.findAll(spec, pageable);

        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationResponseList(notificationPage.getContent());
        
        // Populate creator names
        if (!notificationResponseList.isEmpty()) {
            Set<String> creatorIds = notificationResponseList.stream()
                    .map(NotificationResponse::getCreatedBy)
                    .collect(Collectors.toSet());
            Map<String, User> creators = userRepository.findAllById(creatorIds).stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
            notificationResponseList.forEach(dto -> {
                User creator = creators.get(dto.getCreatedBy());
                if (creator != null) {
                    dto.setCreatedByName(creator.getFullName());
                }
            });
        }

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.NOTIFICATION, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }


    private Competition findCompetitionByIdOrElseThrow(String id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{id}));
    }
}
