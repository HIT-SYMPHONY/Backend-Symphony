package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.EmailService;
import my_computer.backendsymphony.service.ForgotPasswordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Override
    public void forgotPassword(String email) throws NotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException(ErrorMessage.EmailNotFound));

        String temporaryPasswordPlainText = RandomStringUtils.randomAlphanumeric(10);

        user.setTemporaryPassword(passwordEncoder.encode(temporaryPasswordPlainText));

        //user.setTemporaryPassword(temporaryPasswordPlainText);

        user.setTemporaryPasswordExpiredAt(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        String emailBody = "Hello " + user.getUsername() + ",\n\n"
                + "You have requested a new password.\n"
                + "Your temporary password is: " + temporaryPasswordPlainText + "\n\n"
                + "This password will expire in 15 minutes. Please log in and change your password immediately.\n";

        emailService.sendEmail(user.getEmail(), "Your Temporary Password", emailBody);

    }
}
