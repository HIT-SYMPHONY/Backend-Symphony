package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;

import java.util.List;

public interface CommentCompetitionService {
    CommentCompetitionResponse createCommentCompetition(String competitionId, CommentCompetitionRequest request);
    void deleteCommentCompetition(String id);
    PaginationResponseDto<CommentCompetitionResponse> getAllCommentOfCompetition(String competitionId, CommentCompetitionFilterRequest request);
    CommentCompetitionResponse getMyCommentInCompetition(String competitionId);
    CommentCompetitionResponse getCommentCompetitionById(String id);
    CommentCompetitionResponse markCommentCompetition(String id, MarkRequest request);
    CommentCompetitionResponse updateMyComment(String id, CommentCompetitionUpdateRequest request);
}
