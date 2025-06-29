package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
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

    @PostMapping(value = UrlConstant.Classroom.CREATE_CLASSROOM, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClassroom(
            @Valid @RequestPart("data") ClassroomCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(classroomService.createClassroom(request, imageFile));
    }

    @DeleteMapping(UrlConstant.Classroom.DELETE_CLASSROOM)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassroom(@PathVariable String id) {
        classroomService.deleteClassroom(id);
        return  ResponseEntity.noContent().build();
    }
    @PatchMapping(value = UrlConstant.Classroom.UPDATE_CLASSROOM,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    public ResponseEntity<?> updateClassroom(
            @PathVariable String id,
            @RequestPart("data") @Valid ClassroomUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        ClassroomResponse updatedClassroom = classroomService.updateClassroom(id, request,imageFile);
        return VsResponseUtil.success(HttpStatus.OK, updatedClassroom);
    }
}
