package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;

public interface CommentCompetitionService {

    CommentCompetitionResponse createCommentCompetition(CommentCompetitionRequest request);

    CommentCompetitionResponse updateCommentCompetition(String competitionId, CommentCompetitionRequest request);

    CommentCompetitionResponse deleteCommentCompetition(String competitionId);
}
