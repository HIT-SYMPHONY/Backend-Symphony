package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.LessonCreationRequest;
import my_computer.backendsymphony.domain.dto.response.LessonResponse;
import my_computer.backendsymphony.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping(UrlConstant.Lesson.CREATE_LESSON)
    @PreAuthorize("hasRole('ADMIN') or @authz.isClassLeader(authentication, #request.classRoomId)")
    public ResponseEntity<RestData<?>> createLesson(@Valid @RequestBody LessonCreationRequest request){
        LessonResponse createdLesson = lessonService.createLesson(request);
        return VsResponseUtil.success(HttpStatus.CREATED,createdLesson);
    }

    @DeleteMapping(UrlConstant.Lesson.DELETE_LESSON)
    @PreAuthorize("hasRole('ADMIN') or @authz.canModifyLesson(authentication, #lessonId)")
    public ResponseEntity<RestData<?>> deleteLesson(@PathVariable String lessonId){
        lessonService.deleteLesson(lessonId);
        return VsResponseUtil.success("Xóa buổi học thành công!");
    }

}
