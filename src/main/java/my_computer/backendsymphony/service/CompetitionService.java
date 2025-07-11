package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CompetitionCreationRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CompetitionService {
    CompetitionResponse createCompetition(CompetitionCreationRequest request, MultipartFile imageFile);

    CompetitionResponse getCompetitionById(String id);
}
