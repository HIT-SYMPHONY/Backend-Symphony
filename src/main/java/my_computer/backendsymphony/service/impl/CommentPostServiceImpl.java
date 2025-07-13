package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.CommentPost;
import my_computer.backendsymphony.domain.entity.Post;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CommentPostMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CommentPostRepository;
import my_computer.backendsymphony.repository.PostRepository;
import my_computer.backendsymphony.service.CommentPostService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentPostServiceImpl implements CommentPostService {

    private final CommentPostRepository commentPostRepository;
    private final CommentPostMapper commentPostMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    @Override
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
    public CommentPostResponse getCommentPostById(String commentPostId) {
        return null;
    }
}
