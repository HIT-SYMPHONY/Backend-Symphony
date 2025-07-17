package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassRoom, String> {
    boolean existsByName(String name);

    List<ClassRoom> findByLeaderIdOrMembers_Id(String leaderId, String memberId);

    boolean existsByIdAndMembers_Id(String classRoomId, String userId);

    List<ClassRoom> findByNameContainingIgnoreCase(String name);
}
