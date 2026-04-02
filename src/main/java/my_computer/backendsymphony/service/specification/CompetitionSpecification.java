package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import my_computer.backendsymphony.constant.CompetitionStatus;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public final class CompetitionSpecification {
    private CompetitionSpecification() {
    }

    public static Specification<Competition> hasStatus(CompetitionStatus status) {
        if (status == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        LocalDateTime now = LocalDateTime.now();
        return switch (status) {
            case UPCOMING -> (root, query, cb) ->
                    cb.greaterThan(root.get(Competition_.startTime), now);

            case ONGOING -> (root, query, cb) ->
                    cb.and(
                            cb.lessThanOrEqualTo(root.get(Competition_.startTime), now),
                            cb.greaterThanOrEqualTo(root.get(Competition_.endTime), now)
                    );

            case COMPLETED -> (root, query, cb) ->
                    cb.lessThan(root.get(Competition_.endTime), now);

            default -> (root, query, cb) -> cb.conjunction();
        };
    }

    public static Specification<Competition> matchesKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return (root, query, cb) -> {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return cb.like(cb.lower(root.get(Competition_.name)), searchPattern);
        };
    }

    public static Specification<Competition> hasStartYear(Integer year) {
        if (year == null) {
            return null;
        }   

        return (root, query, cb) -> {
            Expression<Integer> yearExpression = cb.function(
                    "YEAR",
                    Integer.class,
                    root.get(Competition_.startTime)
            );
            return cb.equal(yearExpression, year);
        };
    }

    public static Specification<Competition> forUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Competition, CompetitionUser> competitionUserJoin = root.join(Competition_.competitionUsers);
            query.distinct(true);
            return cb.equal(competitionUserJoin.get(CompetitionUser_.user).get(User_.id), userId);
        };
    }

    public static Specification<Competition> hasLeaderId(String leaderId) {
        if (!StringUtils.hasText(leaderId)) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get(Competition_.competitionLeaderId), leaderId);
    }

}
