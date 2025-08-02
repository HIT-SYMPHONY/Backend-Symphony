package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CompetitionMapper;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.CompetitionService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {
    CompetitionRepository competitionRepository;
    CompetitionMapper competitionMapper;
    UploadFileUtil uploadFileUtil;
    UserService userService;
    UserRepository userRepository;

    @Override
    @Transactional
    public CompetitionResponse createCompetition(CompetitionRequest request, MultipartFile imageFile) {

        User user = userRepository.findById(request.getCompetitionLeaderId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID));

        if(user.getRole()!= Role.LEADER){
            throw new InvalidException(ErrorMessage.User.USER_IS_NOT_LEADER);
        }

        if (request.getStartTime().isAfter(request.getEndTime()))
            throw new InvalidException(ErrorMessage.Competition.START_TIME_MUST_BEFORE_END_TIME);
        Competition competition = competitionMapper.toCompetition(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            competition.setImage(imageUrl);
        }
        Competition savedCompetition = competitionRepository.save(competition);
        return competitionMapper.toCompetitionResponse(savedCompetition);
    }

    @Override
    @Transactional
    public CompetitionResponse updateCompetition(String id, CompetitionRequest request, MultipartFile imageFile) {
        Competition competition = findCompetitionByIdOrElseThrow(id);

        UserResponse currentUser = userService.getCurrentUser();
        if(!currentUser.getId().equals(competition.getCompetitionLeaderId()) && currentUser.getRole() != Role.ADMIN){
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
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
        return competitionMapper.toCompetitionResponse(competition);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<CompetitionResponse> getAllCompetitions(PaginationSortRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.COMPETITION);

        Page<Competition> competitionPage = competitionRepository.findAll(pageable);

        List<CompetitionResponse> dtos = competitionMapper.toCompetitionResponseList(competitionPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.COMPETITION, competitionPage);

        return new PaginationResponseDto<>(meta, dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetitionResponse getCompetitionById(String id) {
        Competition competition = findCompetitionByIdOrElseThrow(id);
        return competitionMapper.toCompetitionResponse(competition);
    }

    @Override
    @Transactional
    public void deleteCompetition(String id) {
        Competition competition = findCompetitionByIdOrElseThrow(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isCreatorOrAdmin(competition, authentication))
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        competitionRepository.deleteById(id);
    }



    public boolean isCreatorOrAdmin(Competition competition, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        return currentUserId.equals(competition.getCreatedBy());
    }

    private Competition findCompetitionByIdOrElseThrow(String id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{id}));
    }
}
