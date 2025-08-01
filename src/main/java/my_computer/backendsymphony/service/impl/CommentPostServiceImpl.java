package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.CommentPost;
import my_computer.backendsymphony.domain.entity.Post;
import my_computer.backendsymphony.domain.mapper.CommentPostMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CommentPostRepository;
import my_computer.backendsymphony.repository.PostRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.CommentPostService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentPostServiceImpl implements CommentPostService {

    private final CommentPostRepository commentPostRepository;
    private final CommentPostMapper commentPostMapper;
    private final UserService userService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentPostResponse createCommentPost(CommentPostRequest commentPostRequest) {

        UserResponse currentUser = userService.getCurrentUser();

        Post post = postRepository.findById(commentPostRequest.getPostId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID));

        CommentPost commentPost = commentPostMapper.toEntity(commentPostRequest);
        commentPost.setPost(post);

        CommentPostResponse response = commentPostMapper.toResponse(commentPostRepository.save(commentPost));
        response.setUsername(currentUser.getUsername());

        return response;
    }

    @Override
    @Transactional
    public CommentPostResponse deleteCommentPost(String commentPostId) {
        UserResponse currentUser = userService.getCurrentUser();

        CommentPost commentPost = commentPostRepository.findById(commentPostId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID));

        Post post = commentPost.getPost();
        if (post == null) {
            throw new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID);
        }

        ClassRoom classRoom = post.getClassRoom();

        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(classRoom.getLeaderId())) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        commentPostRepository.delete(commentPost);
        CommentPostResponse response = commentPostMapper.toResponse(commentPost);
        response.setUsername(currentUser.getUsername());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentPostResponse> getCommentPostByPostId(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID));

        return post.getComments().stream()
                .map(comment -> {
                    UserResponse user = userService.getUser(post.getCreatedBy());
                    CommentPostResponse response = commentPostMapper.toResponse(comment);
                    response.setUsername(user.getUsername());
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional
    public CommentPostResponse markCommentPost(MarkRequest markRequest) {
        CommentPost commentPost = commentPostRepository.findById(markRequest.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID));
        String leaderId = commentPost.getPost().getClassRoom().getLeaderId();
        UserResponse currentUser = userService.getCurrentUser();
        if (!currentUser.getId().equals(leaderId) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }
        commentPost.setScore(markRequest.getScore());
        commentPostRepository.save(commentPost);
        CommentPostResponse response = commentPostMapper.toResponse(commentPost);
        response.setUsername(currentUser.getUsername());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentPostResponse> getMyCommentsInPost(String postId) {

        UserResponse currentUser = userService.getCurrentUser();
        List<CommentPost> comments = commentPostRepository.findByPostIdAndCreatedBy(postId, currentUser.getId());

        return comments.stream()
                .map(comment -> {
                    CommentPostResponse response = commentPostMapper.toResponse(comment);
                    response.setUsername(currentUser.getUsername());
                    return response;
                })
                .toList();
    }


}
