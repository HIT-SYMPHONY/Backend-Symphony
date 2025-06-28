package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "classRoom.id", target = "classRoomId")
    NotificationResponse toNotificationResponse(Notification notification);

    @Mapping(target = "classRoom", ignore = true)
    Notification toNotification(NotificationRequest notificationRequest);
}
