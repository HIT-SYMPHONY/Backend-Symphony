package my_computer.backendsymphony.controller;

import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.dto.request.ForgotPasswordRequest;
import my_computer.backendsymphony.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RestApiV1
@RequestMapping("/auth")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    public ResponseEntity<RestData<?>> forgotPassword(ForgotPasswordRequest request){
        boolean isSuccess = forgotPasswordService.forgotPassword(request.getEmail());

        if(isSuccess){
            return VsResponseUtil.success(Map.of("status", "SUCCESS", "message", "Temporary password has been sent to email"));
        }
        else {
            return VsResponseUtil.error(HttpStatus.NOT_FOUND, "Email not registered");
        }
    }

}
