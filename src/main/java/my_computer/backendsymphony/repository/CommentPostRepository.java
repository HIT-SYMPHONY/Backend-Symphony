package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPostRepository extends JpaRepository<CommentPost, String> {

}
