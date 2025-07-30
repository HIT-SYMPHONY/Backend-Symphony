package my_computer.backendsymphony.config;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.*;
import my_computer.backendsymphony.domain.entity.*;
import my_computer.backendsymphony.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClassRoomRepository classRoomRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final LessonRepository lessonRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionUserRepository competitionUserRepository;
    private final CommentPostRepository commentPostRepository;
    private final CommentCompetitionRepository commentCompetitionRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Tạo admin nếu chưa có
        if (!userRepository.existsByStudentCode("2023600666")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Abc123!@#"))
                    .role(Role.ADMIN)
                    .studentCode("2023600666")
                    .email("admin@example.com")
                    .fullName("Quản trị viên")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / Abc123!@#");
        }

        // Nếu đã có user rồi thì không tạo nữa
        if (userRepository.count() > 2) {
            System.out.println(">>> Data already exists, skipping init");
            return;
        }

        // Tạo 2 user mẫu
        User user1 = User.builder()
                .username("nguyen_van_a")
                .firstName("Nguyễn")
                .lastName("Văn A")
                .fullName("Nguyễn Văn A")
                .email("nguyenvana@example.com")
                .password(passwordEncoder.encode("Abc123!@#"))
                .gender(Gender.MALE)
                .dateBirth(LocalDate.of(1995, 5, 20))
                .role(Role.USER)
                .studentCode("2023600555")
                .phoneNumber("0123456789")
                .build();

        User user2 = User.builder()
                .username("tran_thi_b")
                .firstName("Trần")
                .lastName("Thị B")
                .fullName("Trần Thị B")
                .email("tranthib@example.com")
                .password(passwordEncoder.encode("Abc123!@#"))
                .gender(Gender.FEMALE)
                .dateBirth(LocalDate.of(1996, 8, 15))
                .role(Role.LEADER)
                .studentCode("2024600652")
                .phoneNumber("0987654321")
                .build();

        userRepository.saveAll(List.of(user1, user2));

        // Tạo ClassRoom
        ClassRoom classRoom = ClassRoom.builder()
                .name("Lớp Java Backend 2025")
                .startTime(LocalDate.of(2025, 12, 30))
                .duration(6)
                .leaderId(user2.getId())
                .build();

        classRoomRepository.save(classRoom);

        // Gán lớp cho user
        user1.setClassRooms(List.of(classRoom));
        user2.setClassRooms(List.of(classRoom));
        userRepository.saveAll(List.of(user1, user2));

        // Tạo bài đăng (Post)
        Post post = Post.builder()
                .title("Chào mừng đến với khóa học Java Backend")
                .content("Đây là bài viết đầu tiên để chào đón tất cả các bạn học viên.")
                .deadline(LocalDateTime.now().plusDays(7))
                .classRoom(classRoom)
                .createdBy(user2.getId())
                .build();

        postRepository.save(post);

        // Tạo thông báo (Notification)
        Notification notification = Notification.builder()
                .content("Buổi học đầu tiên sẽ diễn ra vào thứ Hai tuần tới.")
                .classRoom(classRoom)
                .createdBy(user2.getId())
                .build();

        notificationRepository.save(notification);

        // Tạo bài học (Lesson)
        Lesson lesson = Lesson.builder()
                .content("Giới thiệu về Java và Spring Boot")
                .location("Phòng 806 A1")
                .timeSlot("Thứ Hai 8:00 - 10:00")
                .classRoom(classRoom)
                .createdBy(user2.getId())
                .build();

        lessonRepository.save(lesson);

        // Tạo cuộc thi (Competition)
        Competition competition = Competition.builder()
                .name("Cuộc thi lập trình Java 2025")
                .content("Giải các bài toán thuật toán bằng Java.")
                .description("Cuộc thi thân thiện giúp nâng cao kỹ năng lập trình.")
                .image("competition1.png")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusWeeks(2))
                .createdBy(user2.getId())
                .competitionLeaderId(user2.getId())
                .build();

        competitionRepository.save(competition);

        // Tạo liên kết CompetitionUser
        CompetitionUser compUser1 = CompetitionUser.builder()
                .competition(competition)
                .user(user1)
                .status(CompetitionUserStatus.REGISTERED)
                .joinedAt(LocalDateTime.now())
                .build();

        CompetitionUser compUser2 = CompetitionUser.builder()
                .competition(competition)
                .user(user2)
                .status(CompetitionUserStatus.REGISTERED)
                .joinedAt(LocalDateTime.now())
                .build();

        competitionUserRepository.saveAll(List.of(compUser1, compUser2));

        // Tạo bình luận bài viết (CommentPost)
        CommentPost commentPost = CommentPost.builder()
                .content("Bài viết đầu tiên rất hay! Rất mong chờ khóa học.")
                .post(post)
                .score(9.0)
                .createdBy(user1.getId())
                .build();

        commentPostRepository.save(commentPost);

        // Tạo bình luận cuộc thi (CommentCompetition)
        CommentCompetition commentCompetition = CommentCompetition.builder()
                .content("Rất hào hứng tham gia cuộc thi này.")
                .score(95.0)
                .competition(competition)
                .createdBy(user1.getId())
                .build();

        commentCompetitionRepository.save(commentCompetition);

        System.out.println(">>> Fake data init completed.");
    }
}
