package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.entity.Notification;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    NotificationResponse deleteNotification(String id);

    PaginationResponseDto<NotificationResponse> getNotificationOfUser(String userId, PaginationRequestDto request);

    NotificationResponse getNotificationOfClass(String classId);
}
