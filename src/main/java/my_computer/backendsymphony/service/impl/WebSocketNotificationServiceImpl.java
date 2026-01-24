package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.service.WebSocketNotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketNotificationServiceImpl implements WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotificationToClassroom(String classroomId, NotificationResponse response) {
        String destination = String.format(UrlConstant.Websocket.NOTIFICATION_CLASSROOM, classroomId);
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, response);
    }

    @Override
    public void sendNotificationToCompetition(String competitionId, NotificationResponse response) {
        String destination = String.format(UrlConstant.Websocket.NOTIFICATION_COMPETITION, competitionId);
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, response);
    }
}
