package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Lesson;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.LessonRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với ID: " + classRoomId));

        return classRoom.getLeaderId() != null && classRoom.getLeaderId().equals(currentUserId);
    }

    public boolean canModifyLesson(Authentication authentication, String lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy buổi học với ID: " + lessonId));

        String classRoomId = lesson.getClassRoom().getId();

        return isClassLeader(authentication, classRoomId);
    }
}
