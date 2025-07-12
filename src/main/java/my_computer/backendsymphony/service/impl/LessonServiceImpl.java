package my_computer.backendsymphony.service.impl;

import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.exception.UnauthorizedException;
import org.springframework.transaction.annotation.Transactional;
import my_computer.backendsymphony.domain.dto.request.LessonUpdateRequest;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void deleteLesson(String lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new NotFoundException("Không tìm thấy buổi học với ID: " + lessonId);
        }
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public LessonResponse updateLesson(String lessonId, LessonUpdateRequest request) {
        Lesson lessonToUpdate = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy buổi học với ID: " + lessonId));

        if (request.getContent() != null) {
            lessonToUpdate.setContent(request.getContent());
        }
        if (request.getLocation() != null) {
            lessonToUpdate.setLocation(request.getLocation());
        }
        if (request.getTimeSlot() != null) {
            lessonToUpdate.setTimeSlot(request.getTimeSlot());
        }

        Lesson updatedLesson = lessonRepository.save(lessonToUpdate);

        return mapToLessonResponseWithDetails(updatedLesson);

    }

    @Override
    @Transactional(readOnly = true)

    public List<LessonResponse> getLessonsByClassRoomId(String classRoomId) {
        if(!classroomRepository.existsById(classRoomId))
            throw new NotFoundException("Không tìm thấy lớp học!");

        List<Lesson> lessons = lessonRepository.findByClassRoomId(classRoomId);

        return lessons.stream()
                .map(this::mapToLessonResponseWithDetails)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> getLessonsForCurrentUser(Authentication authentication) {

        if(authentication == null || !authentication.isAuthenticated())
            throw new UnauthorizedException(ErrorMessage.UNAUTHORIZED);

        String currentUserId = authentication.getName();

        List<Lesson> lessons = lessonRepository.findLessonsByUserId(currentUserId);

        return lessons.stream()
                .map(this::mapToLessonResponseWithDetails)
                .collect(Collectors.toList());
    }


    private LessonResponse mapToLessonResponseWithDetails(Lesson lesson) {

        String leaderName;
        ClassRoom classRoomOfThisLesson = lesson.getClassRoom();

        if (classRoomOfThisLesson != null && classRoomOfThisLesson.getLeaderId() != null) {
            leaderName = userRepository.findById(classRoomOfThisLesson.getLeaderId())
                    .map(User::getFullName)
                    .orElse("Không tìm thấy leader lớp học");
        } else {
            leaderName = "Lớp học chưa có leader";
        }

        LessonResponse finalResponse = new LessonResponse();
        finalResponse.setId(lesson.getId());
        finalResponse.setContent(lesson.getContent());
        finalResponse.setLocation(lesson.getLocation());
        finalResponse.setTimeSlot(lesson.getTimeSlot());
        finalResponse.setCreatedAt(lesson.getCreatedAt());

        finalResponse.setLeaderName(leaderName);
        finalResponse.setClassName(classRoomOfThisLesson.getName());

        return finalResponse;
    }
}
