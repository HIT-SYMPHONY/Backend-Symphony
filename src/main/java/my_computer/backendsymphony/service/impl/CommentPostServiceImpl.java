package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.CommentPostFilterRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.request.CommentPostUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.CommentPost;
import my_computer.backendsymphony.domain.entity.Post;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CommentPostMapper;
import my_computer.backendsymphony.exception.ForbiddenException;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.CommentPostRepository;
import my_computer.backendsymphony.repository.PostRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import my_computer.backendsymphony.service.CommentPostService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentPostServiceImpl implements CommentPostService {

    private final CommentPostRepository commentPostRepository;
    private final CommentPostMapper commentPostMapper;
    private final UserService userService;
    private final PostRepository postRepository;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentPostResponse createCommentPost(String postId, CommentPostRequest commentPostRequest) {

        UserResponse currentUser = userService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));

        if (commentPostRepository.existsByPostIdAndCreatedBy(postId, currentUser.getId())) {
            throw new InvalidException(ErrorMessage.CommentPost.ERR_ALREADY_COMMENT);
        }

        CommentPost commentPost = commentPostMapper.toEntity(commentPostRequest);
        commentPost.setPost(post);

        CommentPost savedComment = commentPostRepository.save(commentPost);
        CommentPostResponse response = commentPostMapper.toResponse(savedComment);
        response.setFullName(currentUser.getFullName());
        response.setStudentCode(currentUser.getStudentCode());
        return response;
    }

    @Override
    @Transactional
    public CommentPostResponse deleteCommentPost(String commentPostId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentPost commentPost = commentPostRepository.findById(commentPostId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID, new String[]{commentPostId}));

        Post post = commentPost.getPost();
        if (post == null) {
            throw new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID);
        }

        ClassRoom classRoom = post.getClassRoom();

        if (!authorizationService.isLeaderOfClassroomOrAdmin(classRoom, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        commentPostRepository.delete(commentPost);
        CommentPostResponse response = commentPostMapper.toResponse(commentPost);
        userRepository.findById(commentPost.getCreatedBy()).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        return response;
    }

    @Override
    @Transactional
    public CommentPostResponse markCommentPost(String commentPostId, MarkRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentPost commentPost = commentPostRepository.findById(commentPostId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.CommentPost.ERR_NOT_FOUND_ID,
                        new String[]{commentPostId}
                ));
        ClassRoom classRoom = commentPost.getPost().getClassRoom();
        if (!authorizationService.isLeaderOfClassroomOrAdmin(classRoom, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        
        commentPostMapper.updateCommentPostFromMark(request, commentPost);
        
        commentPostRepository.save(commentPost);
        CommentPostResponse response = commentPostMapper.toResponse(commentPost);
        userRepository.findById(commentPost.getCreatedBy()).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        return response;
    }

    @Override
    @Transactional
    public CommentPostResponse updateMyComment(String postId, CommentPostUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        CommentPost commentPost = commentPostRepository.findByPostIdAndCreatedBy(postId, currentUserId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID, new String[]{postId}));

        commentPostMapper.updateCommentPostFromContent(request, commentPost);
        CommentPost savedComment = commentPostRepository.save(commentPost);
        
        CommentPostResponse response = commentPostMapper.toResponse(savedComment);
        userRepository.findById(currentUserId).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public CommentPostResponse getMyCommentInPost(String postId) {

        UserResponse currentUser = userService.getCurrentUser();
        CommentPost comment = commentPostRepository.findByPostIdAndCreatedBy(postId, currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID, new String[]{postId}));

        CommentPostResponse response = commentPostMapper.toResponse(comment);
        response.setFullName(currentUser.getFullName());
        response.setStudentCode(currentUser.getStudentCode());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public CommentPostResponse getCommentPostById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentPost commentPost = commentPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CommentPost.ERR_NOT_FOUND_ID, new String[]{id}));

        Post post = commentPost.getPost();
        if (post == null) {
            throw new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID);
        }

        ClassRoom classRoom = post.getClassRoom();

        if (!authorizationService.isLeaderOfClassroomOrAdmin(classRoom, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        CommentPostResponse response = commentPostMapper.toResponse(commentPost);
        userRepository.findById(commentPost.getCreatedBy()).ifPresent(user -> {
            response.setFullName(user.getFullName());
            response.setStudentCode(user.getStudentCode());
        });
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<CommentPostResponse> getAllCommentsOfPost(String postId, CommentPostFilterRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClassRoom classRoom = post.getClassRoom();

        if (!authorizationService.isLeaderOfClassroomOrAdmin(classRoom, authentication)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.COMMENT_POST);
        Page<CommentPost> commentPostPage = commentPostRepository.findByPostId(postId, pageable);

        List<CommentPostResponse> commentPostResponses = commentPostPage.getContent().stream()
                .map(commentPostMapper::toResponse)
                .toList();
        
        populateUserDetails(commentPostResponses);

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.COMMENT_POST, commentPostPage);
        return new PaginationResponseDto<>(meta, commentPostResponses);
    }

    private void populateUserDetails(List<CommentPostResponse> responses) {
        if (responses.isEmpty()) return;
        
        Set<String> userIds = responses.stream()
                .map(CommentPostResponse::getCreatedBy)
                .collect(Collectors.toSet());
        
        Map<String, User> userMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        
        responses.forEach(response -> {
            User user = userMap.get(response.getCreatedBy());
            if (user != null) {
                response.setFullName(user.getFullName());
                response.setStudentCode(user.getStudentCode());
            }
        });
    }

}
