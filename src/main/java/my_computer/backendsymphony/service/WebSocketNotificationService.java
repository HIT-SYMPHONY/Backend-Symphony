package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.response.NotificationResponse;

public interface WebSocketNotificationService {
    void sendNotificationToClassroom(String classroomId, NotificationResponse response);
    void sendNotificationToCompetition(String competitionId, NotificationResponse response);
}
