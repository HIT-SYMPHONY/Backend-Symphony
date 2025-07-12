package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ClassroomStatus;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.ClassroomMapper;
import my_computer.backendsymphony.domain.mapper.UserMapper;
import my_computer.backendsymphony.exception.DuplicateResourceException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ClassroomRepository classroomRepository;
    ClassroomMapper classroomMapper;
    UploadFileUtil uploadFileUtil;

    @Override
    public UserResponse createUser(UserCreationRequest request, MultipartFile imageFile) {

        if (userRepository.existsByStudentCode(request.getStudentCode())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                    new String[]{"Mã sinh viên", request.getStudentCode()});
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                    new String[]{"Email", request.getEmail()});
        }

        User user = userMapper.toUser(request);

        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            user.setImageUrl(imageUrl);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id})));
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request, MultipartFile imageFile) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}));

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                        new String[]{"Email", request.getEmail()});
            }
        }

        if (request.getStudentCode() != null && !request.getStudentCode().equals(user.getStudentCode())) {
            if (userRepository.existsByStudentCode(request.getStudentCode())) {
                throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                        new String[]{"Student Code: ", request.getStudentCode()});
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            user.setImageUrl(imageUrl);
        }

        userMapper.toUser(request, user);

        user.setFullName(user.getFirstName().trim() + " " + user.getLastName().trim());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        String userId = jwt.getSubject();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{userId})
                );
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserResponse(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponse> getMyClasses(String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getSubject();
        List<ClassRoom> allUserClasses = classroomRepository.findByLeaderIdOrMembers_Id(currentUserId, currentUserId);
        List<ClassroomResponse> allClassroomResponses = classroomMapper.toClassroomResponseList(allUserClasses);
        if (!allClassroomResponses.isEmpty()) {
            List<String> leaderIds = allClassroomResponses.stream()
                    .map(ClassroomResponse::getLeaderId).distinct().collect(Collectors.toList());
            Map<String, String> leaderMap = userRepository.findAllById(leaderIds).stream()
                    .collect(Collectors.toMap(User::getId, User::getFullName));
            allClassroomResponses.forEach(response -> {
                String leaderName = leaderMap.get(response.getLeaderId());
                response.setLeaderName(leaderName);
            });
        }
        if (status != null && !status.isBlank()) {
            try {
                ClassroomStatus filterStatus = ClassroomStatus.valueOf(status.toUpperCase());
                return allClassroomResponses.stream()
                        .filter(response -> filterStatus.equals(response.getStatus()))
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                return List.of();
            }
        }
        return allClassroomResponses;
    }

}