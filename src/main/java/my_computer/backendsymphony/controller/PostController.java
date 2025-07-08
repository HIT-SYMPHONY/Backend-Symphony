package my_computer.backendsymphony.controller;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(UrlConstant.Post.POST_COMMON)
    public ResponseEntity<?> createPost(@RequestBody PostRequest request) {
        return VsResponseUtil.success(postService.createPost(request));
    }

}
