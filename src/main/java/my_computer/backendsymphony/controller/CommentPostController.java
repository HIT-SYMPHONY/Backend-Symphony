package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentPostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.service.CommentPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CommentPostController {

    private final CommentPostService commentPostService;

    @PostMapping(UrlConstant.Post.GET_POST_COMMENTS)
    public ResponseEntity<?> createCommentPost(@PathVariable String id, @Valid @RequestBody CommentPostRequest request) {
        return VsResponseUtil.success(commentPostService.createCommentPost(id, request));
    }

    @DeleteMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    public ResponseEntity<?> deleteCommentPost(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.deleteCommentPost(id));
    }

    @GetMapping(UrlConstant.Post.GET_POST_COMMENTS)
    public ResponseEntity<?> getAllCommentsOfPost(@PathVariable String id, @ModelAttribute CommentPostFilterRequest request) {
        return VsResponseUtil.success(commentPostService.getAllCommentsOfPost(id, request));
    }

    @PatchMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    public ResponseEntity<?> updateCommentPost(@PathVariable String id, @Valid @RequestBody MarkRequest request) {
        return VsResponseUtil.success(commentPostService.updateCommentPost(id, request));
    }

    @GetMapping(UrlConstant.Post.GET_MY_POST_COMMENTS)
    public ResponseEntity<?> getMyCommentInPost(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.getMyCommentInPost(id));
    }

}
