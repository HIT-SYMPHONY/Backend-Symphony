package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;

import java.util.List;

public interface CommentPostService {

    CommentPostResponse createCommentPost (CommentPostRequest commentPostRequest);

    CommentPostResponse deleteCommentPost (String commentPostId);

    List<CommentPostResponse> getCommentPostByPostId (String PostId);

    CommentPostResponse markCommentPost (MarkRequest markRequest);
}
