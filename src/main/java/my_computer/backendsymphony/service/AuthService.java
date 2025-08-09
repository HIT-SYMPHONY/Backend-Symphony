package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.LoginRequest;
import my_computer.backendsymphony.domain.dto.request.RefreshTokenRequest;
import my_computer.backendsymphony.domain.dto.request.VerifyCodeRequest;
import my_computer.backendsymphony.domain.dto.response.LoginResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void forgotPassword(String email, String newPassword);
    LoginResponse verifyCodeAndLogin(VerifyCodeRequest request);
    LoginResponse refreshToken(RefreshTokenRequest request);
    UserResponse changePassword (String oldPassword, String newPassword);
    UserResponse verifyPassword (String password);
}
