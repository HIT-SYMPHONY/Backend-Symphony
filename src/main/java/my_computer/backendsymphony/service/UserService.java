package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);
}
