package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.CompetitionFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.NotificationFilterRequest;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionDetailResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionSummaryResponse;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CompetitionService {
    CompetitionDetailResponse createCompetition(CompetitionRequest request, MultipartFile imageFile);

    PaginationResponseDto<CompetitionSummaryResponse> getAllCompetitions(CompetitionFilterRequest request);

    CompetitionResponse getCompetitionById(String id);

    void deleteCompetition(String id);

    CompetitionDetailResponse updateCompetition (String id, CompetitionRequest request , MultipartFile imageFile);

    NotificationResponse createNotification(String competitionId, NotificationRequest request);

    PaginationResponseDto<NotificationResponse> getNotificationsOfCompetition(String id, NotificationFilterRequest request);
}
