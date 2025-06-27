package my_computer.backendsymphony.service.impl;

import my_computer.backendsymphony.domain.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Lesson;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.LessonRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.LessonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ClassRoomRepository classRoomRepository;
    private final LessonMapper lessonMapper;

    @Override
    //@PreAuthorize("hasAnyRole('LEADER', 'ADMIN')")
    public LessonResponse createLesson(LessonCreationRequest request) {
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(()-> new NotFoundException("Không tìm thấy lớp học với ID: " + request.getClassRoomId()));

        Lesson lesson = Lesson.builder()
                .content(request.getContent())
                .location(request.getLocation())
                .timeSlot(request.getTimeSlot())
                .classRoom(classRoom)
                .build();

        Lesson saveLesson = lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(saveLesson);
    }
}
