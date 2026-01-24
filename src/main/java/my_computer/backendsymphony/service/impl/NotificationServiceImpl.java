package my_computer.backendsymphony.service.impl;

import my_computer.backendsymphony.domain.dto.request.NotificationFilterRequest;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.entity.Notification;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.NotificationMapper;
import my_computer.backendsymphony.repository.NotificationRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.NotificationService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.service.specification.NotificationSpecification;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteNotification(String id) {
        // Since notifications are now shared (broadcast), users cannot delete them individually.
        // Only admins or creators (via Classroom/Competition service) should delete them.
        // This endpoint is now invalid in the broadcast model.
        throw new UnsupportedOperationException("Deleting individual notifications is not supported in broadcast mode.");
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<NotificationResponse> getMyNotifications(NotificationFilterRequest requestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        String userId = jwt.getSubject();
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.NOTIFICATION);

        // Fetch notifications from Classrooms the user is part of OR Competitions the user is part of
        Specification<Notification> spec = Specification.where(
                NotificationSpecification.inUserClassrooms(userId)
        ).or(
                NotificationSpecification.inUserCompetitions(userId)
        );
        Page<Notification> notificationPage = notificationRepository.findAll(spec, pageable);
        List<NotificationResponse> notificationResponseList = notificationMapper.toNotificationResponseList(notificationPage.getContent());
        // Populate creator names
        if (!notificationResponseList.isEmpty()) {
            Set<String> creatorIds = notificationResponseList.stream()
                    .map(NotificationResponse::getCreatedBy)
                    .collect(Collectors.toSet());
            Map<String, User> creators = userRepository.findAllById(creatorIds).stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
            notificationResponseList.forEach(dto -> {
                User creator = creators.get(dto.getCreatedBy());
                if (creator != null) {
                    dto.setCreatedByName(creator.getFullName());
                }
            });
        }

        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.NOTIFICATION, notificationPage);
        return new PaginationResponseDto<>(meta, notificationResponseList);
    }
}
