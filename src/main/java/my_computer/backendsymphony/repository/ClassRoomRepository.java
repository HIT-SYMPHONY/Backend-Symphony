package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, String> {
    boolean existsByName(String name);

    List<ClassRoom> findByLeaderIdOrMembers_Id(String leaderId, String memberId);

    boolean existsByIdAndMembers_Id(String classRoomId, String userId);

    List<ClassRoom> findByNameContainingIgnoreCase(String name);

    List<ClassRoom> findByLeaderId(String leaderId);

}
