package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;

import java.util.List;

public interface CommentCompetitionService {

    CommentCompetitionResponse createCommentCompetition(CommentCompetitionRequest request);

    List<CommentCompetitionResponse> getAllCommentOfCompetition(String competitionId);

    CommentCompetitionResponse deleteCommentCompetition(String competitionId);

    CommentCompetitionResponse markCommentCompetition (MarkRequest markRequest);

    List<CommentCompetitionResponse> getMyCommentsInCompetition (String competitionId);
}
