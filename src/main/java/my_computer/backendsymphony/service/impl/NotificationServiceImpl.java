package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse CreateNotification(NotificationRequest request) {
        return null;
    }

    @Override
    public NotificationResponse deleteNotification(String id) {
        return null;
    }

    @Override
    public NotificationResponse getNotificationOfUser(String userID) {
        return null;
    }

    @Override
    public NotificationResponse getNotificationOClass(String classID) {
        return null;
    }
}
