package my_computer.backendsymphony.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import my_computer.backendsymphony.domain.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class NotificationSpecification {

    private NotificationSpecification() {}

    public static Specification<Notification> inUserClassrooms(String userId) {
        if (!StringUtils.hasText(userId)) return null;

        return (root, query, cb) -> {
            Subquery<String> subquery = query.subquery(String.class);
            Root<ClassRoom> subRoot = subquery.from(ClassRoom.class);
            subquery.select(subRoot.get(ClassRoom_.id));
            Join<ClassRoom, User> membersJoin = subRoot.join(ClassRoom_.members);
            subquery.where(cb.equal(membersJoin.get(User_.id), userId));
            return root.get(Notification_.classRoom).get(ClassRoom_.id).in(subquery);
        };
    }
    public static Specification<Notification> inUserCompetitions(String userId) {
        if (!StringUtils.hasText(userId)) return null;
        return (root, query, cb) -> {
            Subquery<String> subquery = query.subquery(String.class);
            Root<Competition> subRoot = subquery.from(Competition.class);
            subquery.select(subRoot.get(Competition_.id));
            Join<Competition, CompetitionUser> compUsersJoin = subRoot.join(Competition_.competitionUsers);
            subquery.where(cb.equal(compUsersJoin.get(CompetitionUser_.user).get(User_.id), userId));
            return root.get(Notification_.competition).get(Competition_.id).in(subquery);
        };
    }
}
