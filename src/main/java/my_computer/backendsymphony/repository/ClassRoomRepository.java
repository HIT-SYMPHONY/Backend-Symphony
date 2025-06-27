package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, String> {
}
