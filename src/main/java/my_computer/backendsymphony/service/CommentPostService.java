package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;

public interface CommentPostService {

    CommentPostResponse createCommentPost (CommentPostRequest commentPostRequest);

    CommentPostResponse deleteCommentPost (String commentPostId);

    CommentPostResponse getCommentPostById (String commentPostId);

}
