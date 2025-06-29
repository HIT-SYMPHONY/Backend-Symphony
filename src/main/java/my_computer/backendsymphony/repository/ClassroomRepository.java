package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassRoom,String> {
    boolean existsByName(String name);
}
