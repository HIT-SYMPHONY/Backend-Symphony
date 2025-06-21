package my_computer.backendsymphony.controller;

import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.dto.request.ForgotPasswordRequest;
import my_computer.backendsymphony.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RestApiV1
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<RestData<?>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        forgotPasswordService.forgotPassword(request.getEmail());
        return VsResponseUtil.success(Map.of("status", "SUCCESS", "message", "Temporary password has been sent to email"));
    }

}
