package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post, String>, JpaSpecificationExecutor<Post> {

    Page<Post> findByClassRoomId (String classRoomId, Pageable pageable);

    List<Post> findByClassRoom_IdInOrderByCreatedAtDesc(List<String> classroomIds);

    @Query("SELECT p.classRoom.id as classroomId, COUNT(p) as postCount FROM Post p WHERE p.classRoom.id IN :classroomIds GROUP BY p.classRoom.id")
    List<Object[]> countPostsByClassroomIds(@Param("classroomIds") List<String> classroomIds);

}
