package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentPostRepository extends JpaRepository<CommentPost, String> {

    @Query("SELECT c FROM CommentPost c WHERE c.post.id = :postId AND c.createdBy = :userId")
    Optional<CommentPost> findByPostIdAndCreatedBy(@Param("postId") String postId, @Param("userId") String userId);

    Page<CommentPost> findByPostId(String postId, Pageable pageable);
    
    boolean existsByPostIdAndCreatedBy(String postId, String userId);
}
