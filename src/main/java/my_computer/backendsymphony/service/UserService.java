package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse getUser(String id);

    UserResponse updateUser(String id, UserUpdateRequest request);

    UserResponse deleteUser(String id);

    UserResponse getCurrentUser();

    List<ClassroomResponse> getMyClasses(String status);
}
