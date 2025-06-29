package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    NotificationResponse deleteNotification(String id);

    NotificationResponse getNotificationOfUser(String userID);

    NotificationResponse getNotificationOClass(String classID);
}
