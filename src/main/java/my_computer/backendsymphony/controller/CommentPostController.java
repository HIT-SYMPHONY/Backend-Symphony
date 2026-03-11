package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.CommentPostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.service.CommentPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CommentPostController {

    private final CommentPostService commentPostService;

    @PostMapping(UrlConstant.CommentPost.GET_POST_COMMENTS)
    public ResponseEntity<?> createCommentPost(@PathVariable String id, @Valid @RequestBody CommentPostRequest request) {
        return VsResponseUtil.success(commentPostService.createCommentPost(id, request));
    }

    @DeleteMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    public ResponseEntity<?> deleteCommentPost(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.deleteCommentPost(id));
    }

    @GetMapping(UrlConstant.CommentPost.GET_POST_COMMENTS)
    public ResponseEntity<?> getAllCommentPostByPostId(@PathVariable String id, @ModelAttribute CommentPostFilterRequest request) {
        return VsResponseUtil.success(commentPostService.getAllCommentsOfPost(id, request));
    }

    @GetMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    public ResponseEntity<?> getCommentPostById(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.getCommentPostById(id));
    }

    @PatchMapping(UrlConstant.CommentPost.COMMENT_POST_ID)
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    public ResponseEntity<?> markCommentPost(@PathVariable String id, @Valid @RequestBody MarkRequest request) {
        return VsResponseUtil.success(commentPostService.markCommentPost(id, request));
    }

    @PatchMapping(UrlConstant.CommentPost.GET_MY_POST_COMMENTS)
    public ResponseEntity<?> updateMyComment(@PathVariable String id, @Valid @RequestBody CommentPostUpdateRequest request) {
        return VsResponseUtil.success(commentPostService.updateMyComment(id, request));
    }

    @GetMapping(UrlConstant.CommentPost.GET_MY_POST_COMMENTS)
    public ResponseEntity<?> getMyCommentInPost(@PathVariable String id) {
        return VsResponseUtil.success(commentPostService.getMyCommentInPost(id));
    }

}
