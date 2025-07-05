package my_computer.backendsymphony.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import my_computer.backendsymphony.constant.Gender;
import my_computer.backendsymphony.constant.Role;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    @Column(name = "student_code", unique = true, updatable = false)
    private String studentCode;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private String intake;

    @Column(name = "temporary_password")
    private String temporaryPassword;

    @Column(name = "temporary_password_expired_at")
    private LocalDateTime temporaryPasswordExpiredAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatRoomUser> chatRoomUsers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_class_room",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_room_id")
    )
    private List<ClassRoom> classRooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CompetitionUser> competitionUsers;

}
