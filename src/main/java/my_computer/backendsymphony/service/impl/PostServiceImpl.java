package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.Post;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.PostMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.ClassRoomRepository;
import my_computer.backendsymphony.repository.PostRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.PostService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ClassRoomRepository classroomRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PostResponse createPost(PostRequest postRequest) {

        ClassRoom classRoom = classroomRepository.findById(postRequest.getClassRoomId())
                .orElseThrow( () ->  new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID) );

        UserResponse user = userService.getCurrentUser();

        if(user.getRole()== Role.LEADER) {
            if(!user.getId().equals(classRoom.getLeaderId())) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }

        Post post = postMapper.toEntity(postRequest);
        post.setClassRoom(classRoom);
        Post savedPost = postRepository.save(post);
        PostResponse response = postMapper.toResponse(savedPost);
        enrichPostResponses(Collections.singletonList(response));
        return response;
    }

    @Override
    @Transactional
    public PostResponse updatePost(PostRequest postRequest, String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID,
                        new String[]{postId}));

        if (postRequest.getClassRoomId() != null) {
            ClassRoom classRoom = classroomRepository.findById(postRequest.getClassRoomId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                            new String[]{postRequest.getClassRoomId()}));
            post.setClassRoom(classRoom);
        }

        UserResponse user = userService.getCurrentUser();

        if(user.getRole()== Role.LEADER) {
            if(!user.getId().equals(post.getClassRoom().getLeaderId()) ) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }
        postMapper.updateEntity(postRequest, post);
        PostResponse response = postMapper.toResponse(post);
        enrichPostResponses(Collections.singletonList(response));
        return response;
    }

    @Override
    @Transactional
    public void deletePost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        UserResponse currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(post.getClassRoom().getLeaderId())) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }
        postRepository.delete(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<PostResponse> getPostsOfClass(String classId, PaginationRequestDto requestDto) {
        ClassRoom classroom = classroomRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                        new String[]{classId}));
        UserResponse currentUser = userService.getCurrentUser();
        boolean isValidMember = classroomRepository.existsByIdAndMembers_Id(classId, currentUser.getId());
        boolean isValidLeader = currentUser.getRole() == Role.LEADER
                && currentUser.getId().equals(classroom.getLeaderId());
        if (!isValidMember && !isValidLeader && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(requestDto);

        Page<Post> postPage = postRepository.findByClassRoomId(classId, pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(postPage.getContent());
        enrichPostResponses(postResponseList);
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, postPage);
        return new PaginationResponseDto<>(meta, postResponseList);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<PostResponse> getAllPosts(PaginationRequestDto requestDto) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto);

        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(postPage.getContent());
        enrichPostResponses(postResponseList);
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, postPage);

        return new PaginationResponseDto<>(meta, postResponseList);
    }


    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById (String postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID,
                        new String[]{postId}));

        ClassRoom classroom = post.getClassRoom();
        String classId = classroom.getId();
        UserResponse currentUser = userService.getCurrentUser();
        boolean isValidMember = classroomRepository.existsByIdAndMembers_Id(classId, currentUser.getId());
        boolean isValidLeader = currentUser.getRole() == Role.LEADER
                && currentUser.getId().equals(classroom.getLeaderId());
        if (!isValidMember && !isValidLeader && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }
        PostResponse response = postMapper.toResponse(post);
        enrichPostResponses(Collections.singletonList(response));
        return response;
    }

    private void enrichPostResponses(List<PostResponse> postResponses) {
        if (postResponses == null || postResponses.isEmpty()) {
            return;
        }

        List<String> creatorIds = postResponses.stream()
                .map(PostResponse::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        Map<String, User> creatorMap = userRepository.findAllById(creatorIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        postResponses.forEach(response -> {
            User creator = creatorMap.get(response.getCreatedBy());
            if (creator != null) {
                response.setCreatorName(creator.getFullName());
            }
        });
    }
}
