package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;

import java.util.List;

public interface CommentCompetitionService {

    CommentCompetitionResponse create(CommentCompetitionRequest request);

    CommentCompetitionResponse delete(String commentId);

    List<CommentCompetitionResponse> getCommentsOfCompetition (String competitionId);
}
