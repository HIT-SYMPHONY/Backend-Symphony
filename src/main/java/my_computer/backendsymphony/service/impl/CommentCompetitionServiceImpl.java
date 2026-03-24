package my_computer.backendsymphony.service.impl;

import lombok.AllArgsConstructor;
import my_computer.backendsymphony.constant.CompetitionUserStatus;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CommentCompetitionMapper;
import my_computer.backendsymphony.exception.ForbiddenException;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.CommentCompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionUserRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import my_computer.backendsymphony.service.CommentCompetitionService;
import my_computer.backendsymphony.service.CompetitionUserService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentCompetitionServiceImpl implements CommentCompetitionService {

    private final CommentCompetitionRepository commentCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final CommentCompetitionMapper commentCompetitionMapper;
    private final CompetitionUserRepository competitionUserRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final CompetitionUserService competitionUserService;

    @Override
    @Transactional
    public CommentCompetitionResponse createCommentCompetition(String competitionId, CommentCompetitionRequest request) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{competitionId}));

        UserResponse currentUser = userService.getCurrentUser();

        // Validate participation
        boolean isParticipating = competitionUserService.isUserParticipating(currentUser.getId(), competitionId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!isParticipating) {
             throw new InvalidException(ErrorMessage.CommentCompetition.CANNOT_COMMENT_BEFORE_REGISTER);
        }

        // Validate competition time
        if (LocalDateTime.now().isBefore(competition.getStartTime()) || LocalDateTime.now().isAfter(competition.getEndTime())) {
            throw new InvalidException(ErrorMessage.Competition.INVALID_TIME_PERIOD);
        }

        CompetitionUser competitionUser = competitionUserRepository
                .findByUser_IdAndCompetition_Id(currentUser.getId(), competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CompetitionUser.ERR_NOT_FOUND));
        competitionUser.setSubmittedAt(LocalDateTime.now());
        competitionUserRepository.save(competitionUser);
        CommentCompetition comment = commentCompetitionMapper.toEntity(request);
        comment.setCompetition(competition);

        CommentCompetition savedComment = commentCompetitionRepository.save(comment);

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(savedComment);

        response.setFullName(currentUser.getFullName());
        response.setStudentCode(currentUser.getStudentCode());

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentCompetitionResponse> getAllCommentOfCompetition(String competitionId) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{competitionId}));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authorizationService.isCompetitionLeaderOrAdmin(competition, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        List<CommentCompetition> comments = commentCompetitionRepository
                .findCommentsByCompetitionId(competitionId);

        List<CommentCompetitionResponse> responses = commentCompetitionMapper.toListResponse(comments);
        populateUserDetails(responses);
        return responses;
    }

    @Override
    @Transactional
    public void deleteCommentCompetition(String id) {
        CommentCompetition  commentCompetition = commentCompetitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID, new String[]{id}));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(!commentCompetition.getCreatedBy().equals(currentUserId) && !isAdmin) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        commentCompetitionRepository.delete(commentCompetition);
    }


    @Override
    @Transactional
    public List<CommentCompetitionResponse> getMyCommentsInCompetition(String competitionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        boolean isJoined = competitionUserRepository.existsByUser_IdAndCompetition_Id(
                currentUserId, competitionId);
        
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isJoined && !isAdmin) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        List<CommentCompetition> comments = commentCompetitionRepository
                .findByUserIdAndCompetitionId(currentUserId, competitionId);

        List<CommentCompetitionResponse> responses = commentCompetitionMapper.toListResponse(comments);
        UserResponse currentUser = userService.getCurrentUser();
        responses.forEach(res -> {
            res.setFullName(currentUser.getFullName());
            res.setStudentCode(currentUser.getStudentCode());
        });
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public CommentCompetitionResponse getCommentCompetitionById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        CommentCompetition commentCompetition = commentCompetitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID, new String[]{id}));

        Competition competition = commentCompetition.getCompetition();

        boolean isCreator = commentCompetition.getCreatedBy().equals(currentUserId);
        boolean isAuthorized = authorizationService.isCompetitionLeaderOrAdmin(competition, authentication);

        if (!isCreator && !isAuthorized) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(commentCompetition);
        userRepository.findById(commentCompetition.getCreatedBy()).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        
        return response;
    }

    @Override
    @Transactional
    public CommentCompetitionResponse markCommentCompetition(String id, MarkRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentCompetition commentCompetition = commentCompetitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID, new String[]{id}));

        Competition competition = commentCompetition.getCompetition();

        if (!authorizationService.isCompetitionLeaderOrAdmin(competition, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        commentCompetitionMapper.updateCommentCompetitionFromMark(request, commentCompetition);
        CommentCompetition savedComment = commentCompetitionRepository.save(commentCompetition);

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(savedComment);
        userRepository.findById(commentCompetition.getCreatedBy()).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        return response;
    }

    @Override
    @Transactional
    public CommentCompetitionResponse updateMyComment(String id, CommentCompetitionUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        CommentCompetition commentCompetition = commentCompetitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID, new String[]{id}));

        Competition competition = commentCompetition.getCompetition();

        if (!commentCompetition.getCreatedBy().equals(currentUserId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        if (LocalDateTime.now().isBefore(competition.getStartTime()) || LocalDateTime.now().isAfter(competition.getEndTime())) {
            throw new InvalidException(ErrorMessage.Competition.INVALID_TIME_PERIOD);
        }

        if (!competitionUserService.isUserParticipating(currentUserId, competition.getId())) {
             throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        commentCompetitionMapper.updateCommentCompetitionFromContent(request, commentCompetition);
        CommentCompetition savedComment = commentCompetitionRepository.save(commentCompetition);

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(savedComment);
        // Use current user details since the caller is the creator
        UserResponse currentUser = userService.getCurrentUser();
        response.setFullName(currentUser.getFullName());
        response.setStudentCode(currentUser.getStudentCode());
        
        return response;
    }

    private void populateUserDetails(List<CommentCompetitionResponse> responses) {
        if (responses.isEmpty()) return;
        
        Set<String> userIds = responses.stream()
                .map(CommentCompetitionResponse::getCreatedBy)
                .collect(Collectors.toSet());
        
        Map<String, User> userMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        responses.forEach(response -> {
            User user = userMap.get(response.getCreatedBy());
            if (user != null) {
                response.setFullName(user.getFullName());
                response.setStudentCode(user.getStudentCode());
            }
        });
    }

}
