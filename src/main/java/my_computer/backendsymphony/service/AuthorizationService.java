package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.Post;
import org.springframework.security.core.Authentication;

public interface AuthorizationService {
    boolean isClassLeader(Authentication authentication, String classRoomId);
    boolean isMemberOfClassroomOrAdmin(ClassRoom classRoom, Authentication authentication);
    boolean isLeaderOfClassroomOrAdmin(ClassRoom classroom, Authentication authentication);
    boolean canViewLesson(Authentication authentication, String lessonId);
    boolean isCreatorOrAdmin(Competition competition, Authentication authentication);
    boolean canModifyPost(Authentication authentication, Post post);
}
