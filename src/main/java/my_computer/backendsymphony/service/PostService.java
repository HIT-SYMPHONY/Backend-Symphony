package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);

    PostResponse updatePost(PostRequest postRequest, String postId);

    PostResponse deletePost(String postId);

    PaginationResponseDto<PostResponse> getPostsOfClass (String classId, PaginationRequestDto requestDto);

    PaginationResponseDto<PostResponse> getAllPosts();
}
