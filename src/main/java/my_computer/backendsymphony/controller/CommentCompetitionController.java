package my_computer.backendsymphony.controller;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.service.CommentCompetitionService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CommentCompetitionController {

    private final CommentCompetitionService commentCompetitionService;

    @PostMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_COMMON)
    public ResponseEntity<?> createCommentCompetition(@RequestBody CommentCompetitionRequest request) {
        return VsResponseUtil.success(commentCompetitionService.createCommentCompetition(request));
    }

    @DeleteMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_ID)
    public ResponseEntity<?> deleteCommentCompetition(@PathVariable String id) {
        return VsResponseUtil.success(commentCompetitionService.deleteCommentCompetition(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    @GetMapping(UrlConstant.CommentCompetition.BY_COMPETITION_ID)
    public ResponseEntity<?> getAllCommentOfCompetition(@PathVariable String competitionId) {
        return VsResponseUtil.success(commentCompetitionService.getAllCommentOfCompetition(competitionId));
    }


}
