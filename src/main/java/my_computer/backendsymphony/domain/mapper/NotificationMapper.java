package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mappings({
        @Mapping(source = "classRoom.id", target = "classRoomId"),
        @Mapping(source = "classRoom.name", target = "classRoomName"),
    })
    NotificationResponse toNotificationResponse(Notification notification);

    @Mapping(target = "classRoom", ignore = true)
    Notification toNotification(NotificationRequest notificationRequest);

    List<NotificationResponse> toNotificationResponseList(List<Notification> notifications);
}
