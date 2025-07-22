package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.service.CommentPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CommentPostController {

    private final CommentPostService commentPostService;

    @PostMapping(UrlConstant.CommentPost.COMMENT_POST_COMMON)
    public ResponseEntity<?> createCommentPost(@Valid @RequestBody CommentPostRequest request) {
        return VsResponseUtil.success(commentPostService.createCommentPost(request));
    }

    @DeleteMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    public ResponseEntity<?> deleteCommentPost(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.deleteCommentPost(id));
    }

    @GetMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    public ResponseEntity<?> getAllCommentPostByPostId(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.getCommentPostByPostId(id));
    }

}
