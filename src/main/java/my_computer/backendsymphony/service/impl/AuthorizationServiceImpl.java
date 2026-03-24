package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.*;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.LessonRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service("authz")
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ClassRoomRepository classroomRepository;
    private final LessonRepository lessonRepository;

    @Override
    public boolean isClassLeader(Authentication authentication, String classRoomId) {

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        String currentUserId = authentication.getName();


        ClassRoom classRoom = classroomRepository.findById(classRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID, new String[]{classRoomId}));

        return classRoom.getLeaderId() != null && classRoom.getLeaderId().equals(currentUserId);
    }

    public boolean canModifyLesson(Authentication authentication, String lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Lesson.ERR_NOT_FOUND_ID, new String[]{lessonId}));

        String classRoomId = lesson.getClassRoom().getId();

        return isClassLeader(authentication, classRoomId);
    }

    public boolean canViewLesson(Authentication authentication, String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Lesson.ERR_NOT_FOUND_ID, new String[]{lessonId}));
        String classRoomId = lesson.getClassRoom().getId();
        return isMemberOfClassroom(classRoomId, authentication) || isClassLeader(authentication, classRoomId);
    }

    public boolean isMemberOfClassroom(String classRoomId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        String currentUserId = authentication.getName();
        ClassRoom classRoom = classroomRepository.findById(classRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID, new String[]{classRoomId}));
        return classRoom.getMembers().stream().anyMatch(member -> member.getId().equals(currentUserId));

    }

    public boolean isMemberOfClassroomOrAdmin(ClassRoom classRoom, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        for (User member : classRoom.getMembers()) {
            if (currentUserId.equals(member.getId())) return true;
        }
        return false;
    }

    public boolean isLeaderOfClassroomOrAdmin(ClassRoom classroom, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        return currentUserId.equals(classroom.getLeaderId());
    }

    public boolean isCreatorOrAdmin(Competition competition, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        return currentUserId.equals(competition.getCreatedBy());
    }

    public boolean isCompetitionLeaderOrAdmin(Competition competition, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        return currentUserId.equals(competition.getCompetitionLeaderId());
    }

    public boolean canModifyPost(Authentication authentication, Post post) {
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        ClassRoom classroom = post.getClassRoom();
        if (classroom == null) {
            return false;
        }
        return isLeaderOfClassroomOrAdmin(classroom, authentication);
    }


}
