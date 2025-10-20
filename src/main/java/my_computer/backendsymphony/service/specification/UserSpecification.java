package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class UserSpecification {

    private UserSpecification() {
    }

    public static Specification<User> hasClassroom(String classroomId) {
        return (root, query, cb) -> {
            Join<User, ClassRoom> classroomJoin = root.join(User_.classRooms);
            return cb.equal(classroomJoin.get(ClassRoom_.id), classroomId);
        };
    }


    public static Specification<User> doesNotHaveClassroom(String classroomId) {
        return (root, query, cb) -> {
            Subquery<ClassRoom> subquery = query.subquery(ClassRoom.class);
            Root<User> subqueryUser = subquery.correlate(root);
            Join<User, ClassRoom> subqueryClassroomJoin = subqueryUser.join(User_.classRooms);
            subquery.where(cb.equal(subqueryClassroomJoin.get(ClassRoom_.id), classroomId));
            subquery.select(subqueryClassroomJoin);
            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<User> isMemberOfCompetition(String competitionId) {
        return (root, query, cb) -> {
            Subquery<CompetitionUser> subquery = query.subquery(CompetitionUser.class);
            Root<CompetitionUser> subqueryRoot = subquery.from(CompetitionUser.class);
            subquery.select(subqueryRoot);

            subquery.where(
                    cb.equal(subqueryRoot.get(CompetitionUser_.user), root),
                    cb.equal(subqueryRoot.get(CompetitionUser_.competition).get(Competition_.id), competitionId)
            );
            return cb.exists(subquery);
        };
    }

    public static Specification<User> isNotMemberOfCompetition(String competitionId) {
        return (root, query, cb) -> {
            Subquery<CompetitionUser> subquery = query.subquery(CompetitionUser.class);
            Root<CompetitionUser> subqueryRoot = subquery.from(CompetitionUser.class);
            subquery.select(subqueryRoot);
            subquery.where(
                    cb.equal(subqueryRoot.get(CompetitionUser_.competition).get(Competition_.id), competitionId),
                    cb.equal(subqueryRoot.get(CompetitionUser_.user),root)
            );
            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<User> hasRole(Role role) {
        if (role == null) return null;
        return (root, query, cb) -> cb.equal(root.get(User_.role), role);
    }

    public static Specification<User> hasIntake(String intake) {
        if (!StringUtils.hasText(intake)) return null;
        return (root, query, cb) -> cb.equal(root.get(User_.intake), intake);
    }

    public static Specification<User> matchesKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return (root, query, cb) -> {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(User_.fullName)), searchPattern),
                    cb.like(cb.lower(root.get(User_.studentCode)), searchPattern)
            );
        };
    }
}
