package my_computer.backendsymphony.config;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Kiểm tra nếu chưa có admin
        if (!userRepository.existsByStudentCode("2023600666")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Abc123!@#"))
                    .role(Role.ADMIN)
                    .studentCode("2023600666")
                    .email("admin@example.com")
                    .fullName("Administrator")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / Abc123!@#");
        }
    }
}
