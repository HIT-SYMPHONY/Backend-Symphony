package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(UrlConstant.Notification.NOTIFICATION_COMMON)
    public ResponseEntity<?> getNotificationOfUser (
            @ModelAttribute PaginationRequestDto request) {
        PaginationResponseDto<NotificationResponse> response = notificationService.getNotificationOfUser(request);
        return VsResponseUtil.success(response);
    }

    @PostMapping(UrlConstant.Notification.NOTIFICATION_ID)
    public ResponseEntity<?> getNotificationByClassId (
            @PathVariable String id,
            @ModelAttribute PaginationRequestDto requestDto){
        PaginationResponseDto<NotificationResponse> response = notificationService.getNotificationOfClass(id, requestDto);
        return VsResponseUtil.success(response);
    }


}
