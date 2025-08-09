package my_computer.backendsymphony.websocket;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationWebSocketSender {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendToClassroom(NotificationResponse notification, String classRoomId) {
        System.out.println("Sending to /topic/classroom/ " + classRoomId);
        messagingTemplate.convertAndSend("/topic/classroom/" + classRoomId, notification);
    }

    public void sendToCompetition(NotificationResponse notification, String competitionId) {
        System.out.println("Sending to /topic/competition/ " + competitionId);
        messagingTemplate.convertAndSend("/topic/competition/" + competitionId, notification);
    }

}
