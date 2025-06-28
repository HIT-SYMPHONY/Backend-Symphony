package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.security.UserPrincipal;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse getUser(String id);

    UserResponse updateUser(String id, UserUpdateRequest request);

    UserResponse deleteUser(String id);

    public UserResponse getCurrentUser();
}
