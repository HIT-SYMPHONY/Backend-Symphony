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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(UrlConstant.Notification.NOTIFICATION_COMMON)
    public ResponseEntity<?> createNotification (@RequestBody @Valid NotificationRequest request){
        NotificationResponse response = notificationService.createNotification(request);
        return VsResponseUtil.success(response);
    }

    @PostMapping(UrlConstant.Notification.NOTIFICATION_ID)
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    public ResponseEntity<?> getNotificationOfUser (
            @PathVariable String userId,
            @Valid @RequestBody PaginationRequestDto request){
        PaginationResponseDto<NotificationResponse> responseDto = notificationService.getNotificationOfUser(userId, request);
        return VsResponseUtil.success(responseDto);
    }


}
