package my_computer.backendsymphony.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import my_computer.backendsymphony.constant.CompetitionUserStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "competition_users")
@IdClass(CompetitionUserId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"competition", "user"})
@EntityListeners(AuditingEntityListener.class)
public class CompetitionUser {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionUserStatus status;

    private String submittedAt;

    @CreatedDate
    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;
}
