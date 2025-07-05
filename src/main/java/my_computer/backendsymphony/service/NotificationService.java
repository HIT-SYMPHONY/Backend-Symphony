package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    NotificationResponse deleteNotification(String id);

    PaginationResponseDto<NotificationResponse> getNotificationOfUser(PaginationRequestDto requestDto);

    PaginationResponseDto<NotificationResponse> getNotificationOfClass(String classRoomId, PaginationRequestDto request);
}
