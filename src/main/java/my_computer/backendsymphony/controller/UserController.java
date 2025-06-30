package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(UrlConstant.User.CREATE_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request));
    }
    @GetMapping(UrlConstant.User.GET_USER)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.getUser(id));
    }

    @PatchMapping(UrlConstant.User.UPDATE_USER)
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        return VsResponseUtil.success(HttpStatus.OK, userService.updateUser(id, request));
    }

    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getCurrentUser());
    }

    @DeleteMapping(UrlConstant.User.DELETE_USER)
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.deleteUser(id));
    }

    @GetMapping(UrlConstant.User.GET_MY_CLASSROOMS)
    public ResponseEntity<?> getMyClasses(
            @RequestParam(value = "status", required = false) String status) {

        List<ClassroomResponse> classrooms = userService.getMyClasses(status);
        return VsResponseUtil.success(classrooms);
    }
}
