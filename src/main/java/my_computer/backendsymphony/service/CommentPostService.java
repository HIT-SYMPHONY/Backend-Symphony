package my_computer.backendsymphony.service;

import jakarta.validation.Valid;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.request.CommentPostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;

import java.util.List;

public interface CommentPostService {

    CommentPostResponse createCommentPost (String postId, CommentPostRequest commentPostRequest);

    CommentPostResponse deleteCommentPost (String commentPostId);

    CommentPostResponse getMyCommentInPost (String postId);

    PaginationResponseDto<CommentPostResponse> getAllCommentsOfPost(String postId, CommentPostFilterRequest request);

    CommentPostResponse updateCommentPost(String id, MarkRequest request);
}
