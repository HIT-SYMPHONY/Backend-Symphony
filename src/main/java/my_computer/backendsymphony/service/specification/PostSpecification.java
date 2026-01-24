package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class PostSpecification {
    public static Specification<Post> matchesKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return (root, query, cb) -> {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return cb.like(cb.lower(root.get(Post_.title)), searchPattern);
        };
    }

    public static Specification<Post> hasClassroomId(String classRoomId) {
        if (!StringUtils.hasText(classRoomId)) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get(Post_.classRoom).get(ClassRoom_.id), classRoomId);
        };
    }

    public static Specification<Post> inUserClassrooms(String userId) {
        return (root, query, cb) -> {
            Join<Post, ClassRoom> classroomJoin = root.join(Post_.classRoom);
            Predicate isLeaderPredicate = cb.equal(classroomJoin.get(ClassRoom_.leaderId), userId);
            Join<ClassRoom, User> membersJoin = classroomJoin.join(ClassRoom_.members);
            Predicate isMemberPredicate = cb.equal(membersJoin.get(User_.id), userId);
            query.distinct(true);
            return cb.or(isLeaderPredicate, isMemberPredicate);
        };
    }

    public static Specification<Post> inParticipatingClassrooms(String userId) {
        if (!StringUtils.hasText(userId)) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Post, ClassRoom> classroomJoin = root.join(Post_.classRoom);
            Join<ClassRoom, User> membersJoin = classroomJoin.join(ClassRoom_.members);
            return cb.equal(membersJoin.get(User_.id), userId);
        };
    }
}
