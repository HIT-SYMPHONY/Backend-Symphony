package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(UrlConstant.Notification.NOTIFICATION_COMMON)
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public ResponseEntity<?> createNotification (@RequestBody @Valid NotificationRequest request){
        NotificationResponse response = notificationService.createNotification(request);
        return VsResponseUtil.success(response);
    }

    @DeleteMapping(UrlConstant.Notification.NOTIFICATION_ID)
    public ResponseEntity<?> deleteNotification (@PathVariable String id){
        NotificationResponse response = notificationService.deleteNotification(id);
        return VsResponseUtil.success(response);
    }

}
