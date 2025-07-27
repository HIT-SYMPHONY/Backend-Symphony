package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = UrlConstant.User.USER_COMMON, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(
            @Valid @RequestPart("data") UserCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request, multipartFile));
    }

    @GetMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.getUser(id));
    }

    @PatchMapping(value = UrlConstant.User.USER_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @Valid @RequestPart("data") UserUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return VsResponseUtil.success(HttpStatus.OK, userService.updateUser(id, request, multipartFile));
    }

    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getCurrentUser());
    }

    @DeleteMapping(UrlConstant.User.USER_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.deleteUser(id));
    }

    @GetMapping(UrlConstant.User.USER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getAllUsers());
    }

    @GetMapping(UrlConstant.User.GET_MY_CLASSROOMS)
    public ResponseEntity<?> getMyClasses(
            @RequestParam(value = "status", required = false) String status) {

        List<ClassroomResponse> classrooms = userService.getMyClasses(status);
        return VsResponseUtil.success(classrooms);
    }

    @GetMapping(UrlConstant.User.GET_MY_COMPETITIONS)
    public ResponseEntity<?> getMyCompetitions(PaginationSortRequestDto request) {
        return VsResponseUtil.success(userService.getMyCompetitions(request));
    }

    @GetMapping(UrlConstant.User.GET_LEADERS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllLeaders() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getLeaderList());
    }
}
