package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    @PostMapping(UrlConstant.Post.POST_COMMON)
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequest request) {
        return VsResponseUtil.success(postService.createPost(request));
    }

    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    @DeleteMapping(UrlConstant.Post.POST_ID)
    public ResponseEntity<?> createPost(@PathVariable String id) {
        return VsResponseUtil.success(postService.deletePost(id));
    }

    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    @PatchMapping(UrlConstant.Post.POST_ID)
    public ResponseEntity<?> updatePost( @RequestBody PostRequest postRequest,
                                         @PathVariable String id) {
        return VsResponseUtil.success(postService.updatePost(postRequest, id));
    }

    @GetMapping(UrlConstant.Post.POST_ID)
    public ResponseEntity<?> getPostOfClass(@PathVariable String id, @ModelAttribute PaginationRequestDto requestDto) {
        return VsResponseUtil.success(postService.getPostsOfClass(id, requestDto));
    }

    @GetMapping(UrlConstant.Post.POST_POST_ID)
    public ResponseEntity<?> getPostById(@PathVariable String postId) {
        return VsResponseUtil.success(postService.getPostById(postId));
    }

}
