package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.JoinCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;

import java.util.List;

public interface CompetitionUserService {
    CompetitionUserResponse joinCompetition(JoinCompetitionRequest request);
    List<CompetitionUserResponse> addMembersToCompetition(AddMembersToCompetitionRequest request);
    List<CompetitionUserResponse> removeMembersFromCompetition(AddMembersToCompetitionRequest request);

}
