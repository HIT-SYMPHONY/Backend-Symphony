package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.NotificationFilterRequest;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @DeleteMapping(UrlConstant.Notification.NOTIFICATION_ID)
    public ResponseEntity<?> deleteNotification (@PathVariable String id){
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(UrlConstant.Notification.GET_MY_NOTIFICATIONS)
    public ResponseEntity<?> getMyNotifications(NotificationFilterRequest request) {
        return VsResponseUtil.success(notificationService.getMyNotifications(request));
    }
}
