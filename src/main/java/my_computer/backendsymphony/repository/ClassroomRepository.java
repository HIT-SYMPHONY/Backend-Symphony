package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<ClassRoom,String> {
    boolean existsByName(String name);
}
