package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.constant.CompetitionUserStatus;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.CompetitionUserId;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionUserRepository extends JpaRepository<CompetitionUser, CompetitionUserId> {

    boolean existsByUser_IdAndCompetition_Id(String userId, String competitionId);

    Optional<CompetitionUser> findByUser_IdAndCompetition_Id(String userId, String competitionId);

    @Query("SELECT cu.user FROM CompetitionUser cu WHERE cu.competition.id = :competitionId")
    Page<User> findUsersByCompetitionId(@Param("competitionId") String competitionId, Pageable pageable);

}
