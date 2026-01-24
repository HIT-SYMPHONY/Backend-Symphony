package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, String>, JpaSpecificationExecutor<Notification> {
    Page<Notification> findByClassRoom_Id(String classroomId, Pageable pageable);
    Page<Notification> findByCompetition_Id(String competitionId, Pageable pageable);
}
