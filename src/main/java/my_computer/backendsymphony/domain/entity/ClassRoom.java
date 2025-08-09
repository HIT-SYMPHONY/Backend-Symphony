package my_computer.backendsymphony.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "class_rooms")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    private String name;

    private String image;

    @Column(name = "start_time")
    private LocalDate startTime;

    private Integer duration;

    private String timeSlot;

    private LocalDate endTime;

    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private String leaderId;

    @ManyToMany(mappedBy = "classRooms", fetch = FetchType.LAZY)
    private List<User> members;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToOne(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatRoom chatRoom;
}
