package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.AddMembersRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.RemoveMembersRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomController {
    ClassroomService classroomService;

    @PostMapping(value = UrlConstant.Classroom.CLASSROOM_COMMON, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClassroom(
            @Valid @RequestPart("data") ClassroomCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(classroomService.createClassroom(request, imageFile));
    }

    @DeleteMapping(UrlConstant.Classroom.CLASSROOM_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassroom(@PathVariable String id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = UrlConstant.Classroom.CLASSROOM_ID, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    public ResponseEntity<?> updateClassroom(
            @PathVariable String id,
            @RequestPart("data") @Valid ClassroomUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        ClassroomResponse updatedClassroom = classroomService.updateClassroom(id, request, imageFile);
        return VsResponseUtil.success(HttpStatus.OK, updatedClassroom);
    }

    @GetMapping(UrlConstant.Classroom.CLASSROOM_ID)
    public ResponseEntity<?> getClassroom(@PathVariable String id) {
        return VsResponseUtil.success(classroomService.getClassroomById(id));
    }

    @GetMapping(UrlConstant.Classroom.CLASSROOM_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllClassrooms(PaginationRequestDto request) {
        PaginationResponseDto<ClassroomResponse> response = classroomService.getAllClassrooms(request);
        return VsResponseUtil.success(response);
    }

    @PostMapping(UrlConstant.Classroom.MEMBERS)
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    public ResponseEntity<?> addMembersToClassroom(
            @PathVariable String id,
            @Valid @RequestBody AddMembersRequest request) {
        return VsResponseUtil.success(classroomService.addMembersToClassroom(id, request));
    }

    @GetMapping(UrlConstant.Classroom.MEMBERS)
    public ResponseEntity<?> getMembersInClassroom(
            @PathVariable String id, PaginationRequestDto request) {
        return VsResponseUtil.success(classroomService.getMembersInClassroom(id, request));
    }

    @DeleteMapping(UrlConstant.Classroom.MEMBERS)
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    public ResponseEntity<Void> removeMembersFromClassroom(
            @PathVariable String id,
            @Valid @RequestBody RemoveMembersRequest request) {

        classroomService.removeMembersFromClassroom(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(UrlConstant.Classroom.CLASSROOM_NAME)
    public ResponseEntity<?> getClassroomByName(@PathVariable String name) {
        return VsResponseUtil.success(classroomService.getClassroomsByName(name));
    }

}
