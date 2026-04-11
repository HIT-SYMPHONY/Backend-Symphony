package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.*;
import my_computer.backendsymphony.domain.dto.response.AddMembersResponse;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;

import my_computer.backendsymphony.domain.dto.response.ClassroomSummaryResponse;
import my_computer.backendsymphony.domain.dto.response.NotificationResponse;
import my_computer.backendsymphony.domain.dto.response.UserSummaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClassroomService {
    ClassroomResponse createClassroom(ClassroomCreationRequest request, MultipartFile imageFile);

    void deleteClassroom(String id);

    ClassroomResponse updateClassroom(String id, ClassroomUpdateRequest request, MultipartFile imageFile);

    ClassroomResponse getClassroomById(String id);

    List<ClassroomResponse> getClassroomsByName(String name);

    PaginationResponseDto<ClassroomResponse> getAllClassrooms(ClassroomFilterRequest request);

    PaginationResponseDto<ClassroomSummaryResponse> getAllClassroomSummariesForCurrentUser(ClassroomFilterRequest request);

    AddMembersResponse addMembersToClassroom(String classroomId, AddMembersRequest request);

    PaginationResponseDto<UserSummaryResponse>  getMembersInClassroom(String classroomId, UserFilterRequest request);

    PaginationResponseDto<UserSummaryResponse> getUsersNotInClassroom(String classroomId, UserFilterRequest request);

    void removeMembersFromClassroom(String classroomId, RemoveMembersRequest request);

    List<ClassroomResponse> getClassroomsOfLeader(ClassroomFilterRequest request);

    List<ClassroomResponse> getClassroomsOfUser (String userId);

    NotificationResponse createNotification(String classroomId, NotificationRequest request);

    PaginationResponseDto<NotificationResponse> getNotificationsOfClassroom(String id, NotificationFilterRequest request);
}
