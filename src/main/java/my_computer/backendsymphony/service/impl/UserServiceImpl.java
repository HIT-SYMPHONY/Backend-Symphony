package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ClassroomStatus;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.UpdateRoleRequest;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.ClassroomMapper;
import my_computer.backendsymphony.domain.mapper.CompetitionMapper;
import my_computer.backendsymphony.domain.mapper.UserMapper;
import my_computer.backendsymphony.exception.DuplicateResourceException;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    ClassRoomRepository classroomRepository;
    ClassroomMapper classroomMapper;
    CompetitionRepository competitionRepository;
    CompetitionMapper competitionMapper;
    UploadFileUtil uploadFileUtil;

    @Override
    @Transactional
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

        user.setUsername(generateUsername(request.getStudentCode()));
        String rawPassword = generatePassword(request.getStudentCode());
        user.setImageUrl("https://res.cloudinary.com/dh6qzqf73/image/upload/v1753189854/lhqcxppwnnm0l4ixrjwz.jpg");
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{id}));

        UserResponse currentUser = this.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        return userMapper.toUserResponse(user);
    }


    @Override
    @Transactional
    public UserResponse updateUser(String id, UserUpdateRequest request, MultipartFile imageFile) {

        UserResponse currentUser = this.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}));

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

        if (request.getDateBirth() != null && request.getDateBirth().isAfter(LocalDate.now())) {
            throw new InvalidException(ErrorMessage.Validation.MUST_IN_PAST);
        }

        userMapper.toUser(request, user);

        user.setFullName(user.getFirstName().trim() + " " + user.getLastName().trim());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserResponse(users);
    }

    @Override
    public List<UserResponse> getUsersByUsername(String username) {

        List<User> userList = userRepository.findByUsernameContaining(username);

        if (userList.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.USERNAME_NOT_FOUND);
        }

        return userMapper.toListUserResponse(userList);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<CompetitionResponse> getMyCompetitions(PaginationSortRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getSubject();
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.COMPETITION);
        Page<Competition> competitionPage = competitionRepository.findByCompetitionUsers_User_Id(currentUserId, pageable);
        List<CompetitionResponse> competitionResponses = competitionMapper.toCompetitionResponseList(competitionPage.getContent());
        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.COMPETITION, competitionPage);
        return new PaginationResponseDto<>(meta, competitionResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(String roleStr) {

        Role role;
        if (roleStr == null || roleStr.isBlank()) {
            role = Role.LEADER;
        } else {
            try {
                role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidException(ErrorMessage.User.INVALID_ROLE);
            }
        }

        List<User> userList = userRepository.findByRole(role);
        return userMapper.toListUserResponse(userList);
    }


    @Override
    @Transactional
    public List<UserResponse> updateRole(UpdateRoleRequest request) {
        Role newRole;
        try {
            newRole = Role.valueOf(request.getRoleStr().toUpperCase());
        } catch (IllegalArgumentException  e) {
            throw new InvalidException(ErrorMessage.User.INVALID_ROLE);
        }

        List<User> users = userRepository.findAllById(request.getUsersId());
        if (users.size() != request.getUsersId().size()) {
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND_ONE_OR_MORE);
        }
        String currentUserId = this.getCurrentUser().getId();

        if (request.getUsersId().contains(currentUserId)) {
            throw new InvalidException(ErrorMessage.User.ILLEGAL);
        }


        users.forEach(user -> user.setRole(newRole));
        userRepository.saveAll(users);
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

    private boolean isStrongPassword(String password) {
        if (password == null) return false;
        return password.length() >= 6 &&
                password.matches(".*[A-Za-z].*") &&
                password.matches(".*\\d.*");
    }

    private String generateUsername (String studentCode) {
        return "sv" + studentCode;  // vd: sv2301012345
    }

    private String generatePassword(String studentCode) {
        // "svHAUI" + 4 số cuối mã SV
        return "svHAUI" + studentCode.substring(studentCode.length() - 4);
    }


}