package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByStudentCode(String studentCode);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.classRooms c WHERE c.id = :classroomId")
    Page<User> findMembersByClassroomId(@Param("classroomId") String classroomId, Pageable pageable);

    List<User> findByRole(Role role);

}
