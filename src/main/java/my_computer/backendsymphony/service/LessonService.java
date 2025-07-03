package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.request.LessonUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(LessonCreationRequest request);
    void deleteLesson(String lessonId);
    LessonResponse updateLesson(String lessonId, LessonUpdateRequest request);
    List<LessonResponse> getLessonsByClassRoomId(String classRoomId);
    List<LessonResponse> getLessonsForCurrentUser(Authentication authentication);
}
