package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping(UrlConstant.User.CREATE_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request));
    }
    @GetMapping(UrlConstant.User.GET_USER)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.OK, userService.getUser(id));
    }
}
