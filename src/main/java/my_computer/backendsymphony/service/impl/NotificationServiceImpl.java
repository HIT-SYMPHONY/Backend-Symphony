package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.NotificationRequest;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.Notification;
import my_computer.backendsymphony.domain.mapper.NotificationMapper;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.service.NotificationService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.websocket.NotificationWebSocketSender;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ClassRoomRepository classroomRepository;
    private final CompetitionRepository competitionRepository;
    private final UserService userService;
    private final NotificationWebSocketSender notificationWebSocketSender;

    @Override
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {

        UserResponse currentUser = userService.getCurrentUser();

        boolean hasClass = request.getClassRoomId() != null;
        boolean hasCompetition = request.getCompetitionId() != null;

        // Chỉ được chọn một trong hai
        if (hasClass == hasCompetition) {
            throw new InvalidException("Notification phải gắn với một trong hai: classRoomId hoặc competitionId.");
        }

        Notification notification = notificationMapper.toNotification(request);
        NotificationResponse response;

        if (hasClass) {
            ClassRoom classRoom = classroomRepository.findById(request.getClassRoomId())
                    .orElseThrow(() -> new NotFoundException(
                            ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                            new String[]{request.getClassRoomId()}
                    ));

            if (currentUser.getRole() != Role.ADMIN && !classRoom.getLeaderId().equals(currentUser.getId())) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }

            notification.setClassRoom(classRoom);
            notificationRepository.save(notification);
            response = notificationMapper.toNotificationResponse(notification);

            response.setClassRoomName(classRoom.getName());

            notificationWebSocketSender.sendToClassroom(response, classRoom.getId());

        } else {
            Competition competition = competitionRepository.findById(request.getCompetitionId())
                    .orElseThrow(() -> new NotFoundException(
                            ErrorMessage.Competition.ERR_NOT_FOUND_ID,
                            new String[]{request.getCompetitionId()}
                    ));

            if (currentUser.getRole() != Role.ADMIN && !competition.getCompetitionLeaderId().equals(currentUser.getId())) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }

            notification.setCompetition(competition);
            notificationRepository.save(notification);
            response = notificationMapper.toNotificationResponse(notification);

            response.setCompetitionName(competition.getName());

            notificationWebSocketSender.sendToCompetition(response, competition.getId());
        }

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
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getNotificationOfUser(PaginationRequestDto requestDto) {

        String userId = userService.getCurrentUser().getId();

        Pageable pageable = PaginationUtil.buildPageable(requestDto);
        Page<Notification> notificationPage = notificationRepository.findByUserId(userId,pageable);

        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationResponseList(notificationPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getNotificationOfClass(String classRoomId, PaginationRequestDto request) {

        if(!classroomRepository.existsById(classRoomId)) {
            throw new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID);
        }

        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<Notification> notificationPage = notificationRepository.findByClassRoom_Id(classRoomId, pageable);

        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationResponseList(notificationPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getNotificationOfCompetition(String competitionId, PaginationRequestDto request) {

        if (!competitionRepository.existsById(competitionId)) {
            throw new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID);
        }

        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<Notification> notificationPage = notificationRepository.findByCompetition_Id(competitionId, pageable);

        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationResponseList(notificationPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }

}
