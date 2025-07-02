package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    Page<Notification> findAllByCreatedBy(String userId, Pageable pageable);

}
