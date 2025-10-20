package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import my_computer.backendsymphony.constant.ClassroomStatus;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public final class ClassroomSpecification {
    private ClassroomSpecification() {
    }

    public static Specification<ClassRoom> hasStatus(ClassroomStatus status) {
        if (status == null) return null;
        LocalDate today = LocalDate.now();
        return switch (status) {
            case UPCOMING -> (root, query, cb) ->
                    cb.greaterThan(root.get(ClassRoom_.startTime), today);
            case ONGOING -> (root, query, cb) ->
                    cb.and(
                            cb.lessThanOrEqualTo(root.get(ClassRoom_.startTime), today),
                            cb.greaterThanOrEqualTo(root.get(ClassRoom_.endTime), today)
                    );
            case COMPLETED -> (root, query, cb) ->
                    cb.lessThan(root.get(ClassRoom_.endTime), today);
        };
    }

    public static Specification<ClassRoom> matchesKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return (root, query, cb) -> {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return cb.like(cb.lower(root.get(ClassRoom_.name)), searchPattern);
        };
    }

    public static Specification<ClassRoom> hasLeaderId(String leaderId) {
        if (!StringUtils.hasText(leaderId)) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get(ClassRoom_.leaderId), leaderId);
    }

    public static Specification<ClassRoom> hasStartYear(Integer year) {
        if (year == null) {
            return null;
        }
        return (root, query, cb) -> {
            Expression<Integer> yearExpression = cb.function(
                    "YEAR",
                    Integer.class,
                    root.get(ClassRoom_.startTime)
            );
            return cb.equal(yearExpression, year);
        };
    }

    public static Specification<ClassRoom> forUser(String userId) {
        return (root, query, cb) -> {
            Predicate isLeaderPredicate = cb.equal(root.get(ClassRoom_.leaderId), userId);
            Join<ClassRoom, User> membersJoin = root.join(ClassRoom_.members);
            Predicate isMemberPredicate = cb.equal(membersJoin.get(User_.id), userId);
            query.distinct(true);
            return cb.or(isLeaderPredicate, isMemberPredicate);
        };
    }
}
