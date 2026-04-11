package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.PostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;
import my_computer.backendsymphony.domain.dto.response.PostWithScoreResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);

    PostResponse updatePost(PostRequest postRequest, String postId);

    void deletePost(String postId);

    List<PostResponse> getPostsOfClass (String classId, PostFilterRequest requestDto);

    List<PostWithScoreResponse> getClassroomPostsWithScore(String classId, PostFilterRequest requestDto);

    PostResponse getPostById(String postId);

    PaginationResponseDto<PostResponse> getAllPosts(PostFilterRequest requestDto);
}
