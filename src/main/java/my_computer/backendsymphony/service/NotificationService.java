package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.NotificationFilterRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;

public interface NotificationService {

    void deleteNotification(String id);

    PaginationResponseDto<NotificationResponse> getMyNotifications(NotificationFilterRequest requestDto);

}
