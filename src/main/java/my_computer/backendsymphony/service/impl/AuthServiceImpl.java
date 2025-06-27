package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.VerifyCodeRequest;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.EmailService;
import my_computer.backendsymphony.domain.dto.request.LoginRequest;
import my_computer.backendsymphony.domain.dto.response.LoginResponse;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.security.UserPrincipal;
import my_computer.backendsymphony.security.jwt.JwtTokenProvider;
import my_computer.backendsymphony.service.AuthService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getStudentCode(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
            return new LoginResponse(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());

        } catch (InternalAuthenticationServiceException | BadCredentialsException ex) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
        }
    }

    @Override
    public void forgotPassword(String email) throws NotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorMessage.EmailNotFound));

        String temporaryPasswordPlainText = RandomStringUtils.randomAlphanumeric(10);

        user.setTemporaryPassword(passwordEncoder.encode(temporaryPasswordPlainText));

        user.setTemporaryPasswordExpiredAt(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        String emailBody = "Hello " + user.getUsername() + ",\n\n"
                + "You have requested a new password.\n"
                + "Your temporary password is: " + temporaryPasswordPlainText + "\n\n"
                + "This password will expire in 15 minutes. Please log in and change your password immediately.\n";

        emailService.sendEmail(user.getEmail(), "Your Temporary Password", emailBody);

    }
    @Override
    public LoginResponse verifyCodeAndLogin(VerifyCodeRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UnauthorizedException("Incorrect code!"));

        if (user.getTemporaryPassword() == null || user.getTemporaryPasswordExpiredAt().isBefore(LocalDateTime.now()))
            throw new UnauthorizedException("Code is invalid or expired!");

        if (!passwordEncoder.matches(request.getTempPassword(), user.getTemporaryPassword()))
            throw new UnauthorizedException("Code is incorrect!");

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
        String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);

        user.setTemporaryPassword(null);
        user.setTemporaryPasswordExpiredAt(null);
        userRepository.save(user);

        return new LoginResponse(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
    }
}
