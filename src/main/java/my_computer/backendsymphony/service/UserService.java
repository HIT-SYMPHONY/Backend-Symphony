package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.*;
import my_computer.backendsymphony.domain.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest request, MultipartFile imageFile);

    UserResponse getUser(String id);

    UserResponse updateUser(String id, UserUpdateRequest request, MultipartFile imageFile);

    UserResponse deleteUser(String id);

    UserResponse getCurrentUser();

    List<ClassroomResponse> getMyClasses(ClassroomFilterRequest request);

    PaginationResponseDto<UserResponse> getAllUsers(UserFilterRequest request);

    List<UserResponse> getUsersByUsername(String username);

    List<CompetitionResponse> getMyCompetitions(CompetitionFilterRequest request);

    List<UserResponse> getUsersByRole (String roleStr);

    List<UserResponse> updateRole(UpdateRoleRequest request);

    PaginationResponseDto<PostResponse> getMyPosts(PostFilterRequest request);

    AdminResetPasswordResponse adminResetPassword(String userId);
}
