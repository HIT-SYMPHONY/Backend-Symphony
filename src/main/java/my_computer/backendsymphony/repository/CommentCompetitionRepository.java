package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCompetitionRepository extends JpaRepository<CommentCompetition,String> {

    @Query("""
        SELECT cc FROM CommentCompetition cc
        WHERE cc.competition.id = :competitionId
        ORDER BY cc.createdAt DESC
    """)
    List<CommentCompetition> findCommentsByCompetitionId(@Param("competitionId") String competitionId);

}

