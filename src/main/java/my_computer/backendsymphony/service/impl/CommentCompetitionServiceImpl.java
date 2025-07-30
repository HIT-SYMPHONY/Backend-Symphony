package my_computer.backendsymphony.service.impl;

import lombok.AllArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import my_computer.backendsymphony.domain.entity.CommentPost;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.mapper.CommentCompetitionMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CommentCompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.service.CommentCompetitionService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentCompetitionServiceImpl implements CommentCompetitionService {

    private final CommentCompetitionRepository commentCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final CommentCompetitionMapper commentCompetitionMapper;
    private final UserService userService;

    @Override
    public CommentCompetitionResponse createCommentCompetition(CommentCompetitionRequest request) {

        Competition competition = competitionRepository.findById(request.getCompetitionId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        CommentCompetition comment = commentCompetitionMapper.toEntity(request);
        comment.setCompetition(competition);

        CommentCompetition savedComment = commentCompetitionRepository.save(comment);

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(savedComment);

        String username = userService.getCurrentUser().getUsername();
        response.setCreatedByUserName(username);

        return response;
    }


    @Override
    public List<CommentCompetitionResponse> getAllCommentOfCompetition(String competitionId) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        UserResponse currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ADMIN) {
            if(competition.getCompetitionLeaderId().equals(currentUser.getId())){
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }

        List<CommentCompetition> comments = commentCompetitionRepository
                .findCommentsByCompetitionId(competitionId);

        return commentCompetitionMapper.toListResponse(comments);
    }

    @Override
    public CommentCompetitionResponse deleteCommentCompetition(String competitionId) {
        CommentCompetition  commentCompetition = commentCompetitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID));
        UserResponse currentUser = userService.getCurrentUser();
        if(!commentCompetition.getCreatedBy().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN ) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        commentCompetitionRepository.delete(commentCompetition);
        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(commentCompetition);
        String username = userService.getUser(commentCompetition.getCreatedBy()).getUsername();
        response.setCreatedByUserName(username);
        return response;
    }

    @Override
    public CommentCompetitionResponse markCommentCompetition(MarkRequest markRequest) {
        CommentCompetition commentCompetition = commentCompetitionRepository.findById(markRequest.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentCompetition.ERR_NOT_FOUND_ID));

        String leaderId = commentCompetition.getCompetition().getCompetitionLeaderId();
        UserResponse currentUser = userService.getCurrentUser();
        if (!currentUser.getId().equals(leaderId) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }
        commentCompetition.setScore(markRequest.getScore());
        commentCompetitionRepository.save(commentCompetition);
        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(commentCompetition);
        response.setCreatedByUserName(currentUser.getUsername());
        return response;
    }
}
