package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CompetitionUserRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;

public interface CompetitionUserService {
    CompetitionUserResponse joinCompetition(CompetitionUserRequest request);
}
