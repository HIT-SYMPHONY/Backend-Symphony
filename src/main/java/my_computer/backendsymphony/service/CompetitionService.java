package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.request.CompetitionCreationRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CompetitionService {
    CompetitionResponse createCompetition(CompetitionCreationRequest request, MultipartFile imageFile);

    PaginationResponseDto<CompetitionResponse> getAllCompetitions(PaginationSortRequestDto request);

    CompetitionResponse getCompetitionById(String id);

    void deleteCompetition(String id);
}
