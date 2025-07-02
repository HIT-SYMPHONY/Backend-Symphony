package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.AddMembersRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.AddMembersResponse;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ClassroomService {
    ClassroomResponse createClassroom(ClassroomCreationRequest request, MultipartFile imageFile);

    void deleteClassroom(String id);

    ClassroomResponse updateClassroom(String id, ClassroomUpdateRequest request, MultipartFile imageFile);

    ClassroomResponse getClassroomById(String id);

    PaginationResponseDto<ClassroomResponse> getAllClassrooms(PaginationRequestDto request);

    AddMembersResponse addMembersToClassroom(String classroomId, AddMembersRequest request);
}
