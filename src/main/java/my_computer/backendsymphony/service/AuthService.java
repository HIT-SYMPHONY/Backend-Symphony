package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.LoginRequest;
import my_computer.backendsymphony.domain.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
