package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CompetitionFilterRequest;
import my_computer.backendsymphony.domain.dto.request.JoinCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.UserFilterRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.dto.response.UserSummaryResponse;

import java.util.List;

public interface CompetitionUserService {
    CompetitionUserResponse joinCompetition(JoinCompetitionRequest request);
    List<CompetitionUserResponse> addMembersToCompetition(AddMembersToCompetitionRequest request);
    List<CompetitionUserResponse> removeMembersFromCompetition(AddMembersToCompetitionRequest request);

    PaginationResponseDto<UserSummaryResponse> getMembersCompetition(String competitionId, UserFilterRequest request);
    PaginationResponseDto<UserSummaryResponse> getNonMembersCompetition(String competitionId, UserFilterRequest request);
}
