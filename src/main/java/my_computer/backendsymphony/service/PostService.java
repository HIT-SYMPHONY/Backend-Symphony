package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.PostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.request.PostUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);

    PostResponse updatePost(PostUpdateRequest postRequest, String postId);

    void deletePost(String postId);

    PaginationResponseDto<PostResponse> getPostsOfClass (String classId, PostFilterRequest requestDto);

    PostResponse getPostById(String postId);

    PaginationResponseDto<PostResponse> getAllPosts(PaginationRequestDto requestDto);
}
