package my_computer.backendsymphony.service.impl;

import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Lesson;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.LessonRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.LessonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ClassroomRepository classroomRepository;
    private final LessonMapper lessonMapper;
    private final UserRepository userRepository;

    @Override
    public LessonResponse createLesson(LessonCreationRequest request) {
        ClassRoom classRoom = classroomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học"));

        Lesson lesson = lessonMapper.toLesson(request);
        lesson.setClassRoom(classRoom);
        Lesson savedLesson = lessonRepository.save(lesson);

        return mapToLessonResponseWithDetails(savedLesson);
    }


    private LessonResponse mapToLessonResponseWithDetails(Lesson lesson) {
        String lessonCreatorName = userRepository.findByUsername(lesson.getCreatedBy())
                .map(User::getFullName)
                .orElse("SYSTEM");

        LessonResponse finalResponse = new LessonResponse();
        finalResponse.setId(lesson.getId());
        finalResponse.setLeaderName(lessonCreatorName);
        finalResponse.setContent(lesson.getContent());
        finalResponse.setLocation(lesson.getLocation());
        finalResponse.setTimeSlot(lesson.getTimeSlot());
        finalResponse.setCreatedAt(lesson.getCreatedAt());
        finalResponse.setClassName(lesson.getClassRoom().getName());

        return finalResponse;
    }
}
