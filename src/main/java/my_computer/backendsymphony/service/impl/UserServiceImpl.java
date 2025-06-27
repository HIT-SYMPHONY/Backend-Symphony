package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.UserMapper;
import my_computer.backendsymphony.exception.DuplicateResourceException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByStudentCode(request.getStudentCode())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                    new String[]{"Mã sinh viên", request.getStudentCode()});
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE,
                    new String[]{"Email", request.getEmail()});
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id})));
    }
}