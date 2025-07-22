package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    Page<Notification> findByClassRoom_Id(String classRoomId, Pageable pageable);


    @Query("""
        SELECT n FROM Notification n
        JOIN n.classRoom c
        JOIN c.members m
        WHERE m.id = :userId
    """)
    Page<Notification> findByUserId(String userId, Pageable pageable);

}
