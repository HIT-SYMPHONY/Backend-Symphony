package my_computer.backendsymphony.service.specification;

import my_computer.backendsymphony.domain.entity.ClassRoom_;
import my_computer.backendsymphony.domain.entity.Lesson;
import my_computer.backendsymphony.domain.entity.Lesson_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class LessonSpecification {
    public static Specification<Lesson> matchesKeyword(String keyword) {
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(Lesson_.title)), searchPattern);
    }

    public static Specification<Lesson> hasClassroomId(String classroomId) {
        if (!StringUtils.hasText(classroomId)) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get(Lesson_.classRoom).get(ClassRoom_.id), classroomId);
    }


}
