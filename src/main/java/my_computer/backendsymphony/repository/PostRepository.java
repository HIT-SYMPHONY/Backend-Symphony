package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    Page<Post> findByClassRoomId (String classRoomId, Pageable pageable);

    List<Post> findByClassRoom_IdInOrderByCreatedAtDesc(List<String> classroomIds);

}
