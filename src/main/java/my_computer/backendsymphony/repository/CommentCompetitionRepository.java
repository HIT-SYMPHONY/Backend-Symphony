package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentCompetitionRepository extends JpaRepository<CommentCompetition,String> {
}
