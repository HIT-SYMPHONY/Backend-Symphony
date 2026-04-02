package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class CompetitionUserSpecification {

    private CompetitionUserSpecification() {
    }

    public static Specification<CompetitionUser> isMemberOfCompetition(String competitionId) {
        return (root, query, cb) -> cb.equal(root.get(CompetitionUser_.competition).get(Competition_.id), competitionId);
    }

    public static Specification<CompetitionUser> withUserFetched() {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch(CompetitionUser_.user, JoinType.INNER);
            }
            return null;
        };
    }

    public static Specification<CompetitionUser> hasRole(Role role) {
        if (role == null) return null;
        return (root, query, cb) -> {
            Join<CompetitionUser, User> userJoin = root.join(CompetitionUser_.user);
            return cb.equal(userJoin.get(User_.role), role);
        };
    }

    public static Specification<CompetitionUser> hasIntake(String intake) {
        if (!StringUtils.hasText(intake)) return null;
        return (root, query, cb) -> {
            Join<CompetitionUser, User> userJoin = root.join(CompetitionUser_.user);
            return cb.equal(userJoin.get(User_.intake), intake);
        };
    }

    public static Specification<CompetitionUser> matchesKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return (root, query, cb) -> {
            Join<CompetitionUser, User> userJoin = root.join(CompetitionUser_.user);
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(userJoin.get(User_.fullName)), searchPattern),
                    cb.like(cb.lower(userJoin.get(User_.studentCode)), searchPattern)
            );
        };
    }
}
