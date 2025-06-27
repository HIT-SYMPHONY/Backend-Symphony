package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    List<Lesson> findByClassRoomId(String classRoomId);

    @Query("SELECT l FROM Lesson l WHERE l.classRoom.id IN " +
            "(SELECT c.id FROM User u JOIN u.classRooms c WHERE u.id = :userId) " +
            "ORDER BY l.createdAt ASC")
    List<Lesson> findLessonsByUserId(@Param("userId") String userId);
}
