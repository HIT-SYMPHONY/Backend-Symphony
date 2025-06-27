package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.AuthRequest;
import my_computer.backendsymphony.domain.dto.request.LoginRequest;
import my_computer.backendsymphony.domain.dto.request.VerifyCodeRequest;
import my_computer.backendsymphony.domain.dto.response.LoginResponse;
import my_computer.backendsymphony.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest request) {
        return VsResponseUtil.success(HttpStatus.OK, authService.login(request));
    }

    @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD)
    public ResponseEntity<RestData<?>> forgotPassword(@Valid @RequestBody AuthRequest request){
        authService.forgotPassword(request.getEmail());
        return VsResponseUtil.success(Map.of("status", "SUCCESS", "message", "Temporary password has been sent to email"));
    }
    @PostMapping(UrlConstant.Auth.VERIFY_TEMPPASSWORD)
    public ResponseEntity<RestData<?>> handleVerifyCodeAndLogin(@Valid @RequestBody VerifyCodeRequest request) {
        LoginResponse loginResponse = authService.verifyCodeAndLogin(request);
        return VsResponseUtil.success(loginResponse);
    }
}
