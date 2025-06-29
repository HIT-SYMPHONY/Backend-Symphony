package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ClassroomService {
    ClassroomResponse createClassroom(ClassroomCreationRequest request, MultipartFile imageFile);

    void deleteClassroom(String id);

    ClassroomResponse updateClassroom(String id, ClassroomUpdateRequest request,MultipartFile imageFile);

    boolean isLeaderOfClassroom(ClassRoom classRoom, Authentication authentication);
}
