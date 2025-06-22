package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.dto.request.AuthRequest;
import my_computer.backendsymphony.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {

    private final AuthService forgotPasswordService;

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<RestData<?>> forgotPassword(@Valid @RequestBody AuthRequest request){
        forgotPasswordService.forgotPassword(request.getEmail());
        return VsResponseUtil.success(Map.of("status", "SUCCESS", "message", "Temporary password has been sent to email"));
    }

}
