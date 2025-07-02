package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Notification;
import my_computer.backendsymphony.domain.mapper.NotificationMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.security.UserPrincipal;
import my_computer.backendsymphony.service.NotificationService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public NotificationResponse createNotification(NotificationRequest request) {


        ClassRoom classRoom = classroomRepository.findById(request.getClassRoomId())
                .orElseThrow( ()-> new NotFoundException(
                    ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                    new String[]{
                            request.getClassRoomId()
                    }
            ));

        UserResponse currentUser = userService.getCurrentUser();
        String currentUserId = currentUser.getId();
        String currentUserName = currentUser.getUsername();

        Notification notification = notificationMapper.toNotification(request);
        notification.setCreatedBy(currentUserId);
        notification.setClassRoom(classRoom);

        notificationRepository.save(notification);
        NotificationResponse response = notificationMapper.toNotificationResponse(notification);
        response.setCreatedByUsername(currentUserName);
        return response;
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
