package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CompetitionService {
    CompetitionResponse createCompetition(CompetitionRequest request, MultipartFile imageFile);

    PaginationResponseDto<CompetitionResponse> getAllCompetitions(PaginationSortRequestDto request);

    CompetitionResponse getCompetitionById(String id);

    void deleteCompetition(String id);

    CompetitionResponse updateCompetition (String id, CompetitionRequest request , MultipartFile imageFile);

}
