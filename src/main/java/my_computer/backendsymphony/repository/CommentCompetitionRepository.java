package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentCompetitionRepository extends JpaRepository<CommentCompetition, String> {

    Page<CommentCompetition> findAllByCompetitionId(String competitionId, Pageable pageable);

}
