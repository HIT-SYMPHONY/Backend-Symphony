package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentCompetitionRepository extends JpaRepository<CommentCompetition,String> {

    @Query("""
        SELECT cc FROM CommentCompetition cc
        WHERE cc.competition.id = :competitionId
    """)
    Page<CommentCompetition> findCommentsByCompetitionId(@Param("competitionId") String competitionId, Pageable pageable);

    @Query("SELECT c FROM CommentCompetition c WHERE c.createdBy = :userId AND c.competition.id = :competitionId")
    Optional<CommentCompetition> findByUserIdAndCompetitionId(@Param("userId") String userId,
                                                          @Param("competitionId") String competitionId);

    boolean existsByCompetition_IdAndCreatedBy(String competitionId, String userId);
}
