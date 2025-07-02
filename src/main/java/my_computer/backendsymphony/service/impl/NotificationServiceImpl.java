package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.MessageContent;
import my_computer.backendsymphony.domain.entity.Notification;
import my_computer.backendsymphony.domain.mapper.NotificationMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.security.UserPrincipal;
import my_computer.backendsymphony.service.NotificationService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;

    @Override
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {

        UserResponse currentUser = userService.getCurrentUser();

        ClassRoom classRoom = classroomRepository.findById(request.getClassRoomId())
                .orElseThrow( ()-> new NotFoundException(
                    ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                    new String[]{ request.getClassRoomId() }
                ));

        if(currentUser.getRole() != Role.ADMIN) {
            if(!classRoom.getLeaderId().equals(currentUser.getId())){
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }

        String currentUserName = currentUser.getUsername();

        Notification notification = notificationMapper.toNotification(request);
        notification.setClassRoom(classRoom);

        notificationRepository.save(notification);
        NotificationResponse response = notificationMapper.toNotificationResponse(notification);
        response.setCreatedByUsername(currentUserName);
        return response;
    }

    @Override
    @Transactional
    public NotificationResponse deleteNotification(String id) {

        UserResponse currentUser = userService.getCurrentUser();

        Notification notification = notificationRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(ErrorMessage.Notification.ERR_NOT_FOUND_ID)
        );

        if(currentUser.getRole() != Role.ADMIN) {
            if(!notification.getCreatedBy().equals(currentUser.getId())){
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }

        notificationRepository.delete(notification);
        return notificationMapper.toNotificationResponse(notification);
    }

    @Override
    public NotificationResponse getNotificationOfUser(String userID) {
        return null;
    }

    @Override
    public NotificationResponse getNotificationOfClass(String classID) {
        return null;
    }
}
