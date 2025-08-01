package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.CompetitionUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionUserRepository extends JpaRepository<CompetitionUser, CompetitionUserId> {

    boolean existsByUser_IdAndCompetition_Id(String userId, String competitionId);

    Optional<CompetitionUser> findByUser_IdAndCompetition_Id(String userId, String competitionId);

}
