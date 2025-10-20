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
            Subquery<String> subquery = query.subquery(String.class);
            Root<ClassRoom> subqueryRoot = subquery.from(ClassRoom.class);
            subquery.select(subqueryRoot.get(ClassRoom_.id));
            Predicate isLeaderPredicate = cb.equal(subqueryRoot.get(ClassRoom_.leaderId), userId);
            Join<ClassRoom, User> membersJoin = subqueryRoot.join(ClassRoom_.members);
            Predicate isMemberPredicate = cb.equal(membersJoin.get(User_.id), userId);
            subquery.where(cb.or(isLeaderPredicate, isMemberPredicate));
            subquery.distinct(true);
            return root.get(Post_.classRoom).get(ClassRoom_.id).in(subquery);
        };
    }
}
