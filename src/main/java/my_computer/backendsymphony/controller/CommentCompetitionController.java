package my_computer.backendsymphony.controller;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.service.CommentCompetitionService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class CommentCompetitionController {

    private final CommentCompetitionService commentCompetitionService;

    @PostMapping(UrlConstant.CommentCompetition.COMMENT_COMPETITION_COMMON)
    public ResponseEntity<?> createCommentCompetition(@RequestBody CommentCompetitionRequest request) {
        return VsResponseUtil.success(commentCompetitionService.createCommentCompetition(request));
    }

}
