package my_computer.backendsymphony.repository;

import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.CompetitionUserId;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompetitionUserRepository extends JpaRepository<CompetitionUser, CompetitionUserId>, JpaSpecificationExecutor<CompetitionUser> {

    boolean existsByUser_IdAndCompetition_Id(String userId, String competitionId);

    Optional<CompetitionUser> findByUser_IdAndCompetition_Id(String userId, String competitionId);

    @Query("SELECT cu.user FROM CompetitionUser cu WHERE cu.competition.id = :competitionId")
    Page<User> findUsersByCompetitionId(@Param("competitionId") String competitionId, Pageable pageable);

    @Query("SELECT cu.competition.id FROM CompetitionUser cu WHERE cu.user.id = :userId AND cu.competition.id IN :competitionIds")
    Set<String> findRegisteredCompetitionIds(
            @Param("userId") String userId,
            @Param("competitionIds") List<String> competitionIds
    );

    List<CompetitionUser> findByUser_IdAndCompetition_IdIn(String currentUserId, List<String> compIds);
}
