package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.service.CommentCompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CommentCompetitionController {

    private final CommentCompetitionService commentCompetitionService;

    @PostMapping(UrlConstant.CommentCompetition.GET_COMPETITION_COMMENTS)
    public ResponseEntity<?> createCommentCompetition(@PathVariable String id, @Valid @RequestBody CommentCompetitionRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED,commentCompetitionService.createCommentCompetition(id, request));
    }

    @DeleteMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_ID)
    public ResponseEntity<Void> deleteCommentCompetition(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping(UrlConstant.CommentCompetition.GET_COMPETITION_COMMENTS)
    public ResponseEntity<?> getAllCommentsOfCompetition(@PathVariable String id, CommentCompetitionFilterRequest request) {
        return VsResponseUtil.success(commentCompetitionService.getAllCommentOfCompetition(id, request));
    }

    @GetMapping(UrlConstant.CommentCompetition.GET_MY_COMPETITION_COMMENTS)
    public ResponseEntity<?> getMyCommentsInCompetition(@PathVariable String id) {
        return VsResponseUtil.success(commentCompetitionService.getMyCommentInCompetition(id));
    }

    @GetMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_ID)
    public ResponseEntity<?> getCommentCompetitionById(@PathVariable String id) {
        return VsResponseUtil.success(commentCompetitionService.getCommentCompetitionById(id));
    }

    @PatchMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_ID)
    public ResponseEntity<?> markCommentCompetition(@PathVariable String id, @Valid @RequestBody MarkRequest request) {
        return VsResponseUtil.success(commentCompetitionService.markCommentCompetition(id, request));
    }

    @PatchMapping(UrlConstant.CommentCompetition.GET_MY_COMPETITION_COMMENTS)
    public ResponseEntity<?> updateMyComment(@PathVariable String id, @Valid @RequestBody CommentCompetitionUpdateRequest request) {
        return VsResponseUtil.success(commentCompetitionService.updateMyComment(id, request));
    }
}
