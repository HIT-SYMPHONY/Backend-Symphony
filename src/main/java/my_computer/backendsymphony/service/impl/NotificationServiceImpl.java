package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
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
import my_computer.backendsymphony.service.NotificationService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
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
    @Transactional
    public NotificationResponse deleteNotification(String id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getNotificationOfUser(String userId, PaginationRequestDto request){
        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<Notification> notificationPage = notificationRepository.findAllByCreatedBy(userId,pageable);

        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationList(notificationPage.getContent());

        if(!notificationResponseList.isEmpty()){

        }

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }

    @Override
    public NotificationResponse getNotificationOfClass(String classID) {
        return null;
    }
}
