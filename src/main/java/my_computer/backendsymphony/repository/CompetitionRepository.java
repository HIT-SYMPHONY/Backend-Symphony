package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, String> {
}
