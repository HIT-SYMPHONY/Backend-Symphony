package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.request.LessonUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;

public interface LessonService {
    LessonResponse createLesson(LessonCreationRequest request);
}
