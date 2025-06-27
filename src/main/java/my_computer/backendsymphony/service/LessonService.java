package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.request.LessonUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(LessonCreationRequest request);
}
