package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public UserResponse createUser(UserCreationRequest request, MultipartFile imageFile);

    UserResponse getUser(String id);

    UserResponse updateUser(String id, UserUpdateRequest request, MultipartFile imageFile);

    UserResponse deleteUser(String id);

    public UserResponse getCurrentUser();

    public List<UserResponse> getAllUsers();
}
