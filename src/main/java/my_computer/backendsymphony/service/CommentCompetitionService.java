package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;

import java.util.List;

public interface CommentCompetitionService {
    CommentCompetitionResponse createCommentCompetition(String competitionId, CommentCompetitionRequest request);
    void deleteCommentCompetition(String id);
    List<CommentCompetitionResponse> getAllCommentOfCompetition(String competitionId);
    List<CommentCompetitionResponse> getMyCommentsInCompetition(String competitionId);
    CommentCompetitionResponse getCommentCompetitionById(String id);
    CommentCompetitionResponse markCommentCompetition(String id, MarkRequest request);
    CommentCompetitionResponse updateMyComment(String id, CommentCompetitionUpdateRequest request);
}
