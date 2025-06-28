package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.security.UserPrincipal;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(UrlConstant.User.USER_COMMON)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request));
    }
    @GetMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.getUser(id));
    }

    @PatchMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        return VsResponseUtil.success(HttpStatus.OK, userService.updateUser(id, request));
    }

    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getCurrentUser());
    }

    @DeleteMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.deleteUser(id));
    }

    @GetMapping(UrlConstant.User.USER_COMMON)
    public ResponseEntity<?> getAllUser() {
        return VsResponseUtil.success(HttpStatus.OK, userService.getAllUsers());
    }
}
