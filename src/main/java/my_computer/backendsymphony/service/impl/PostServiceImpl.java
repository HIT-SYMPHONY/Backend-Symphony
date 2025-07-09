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
import my_computer.backendsymphony.domain.mapper.PostMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.PostRepository;
import my_computer.backendsymphony.service.PostService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;

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
        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponse updatePost(PostRequest postRequest, String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID));

        if (postRequest.getClassRoomId() != null) {
            ClassRoom classRoom = classroomRepository.findById(postRequest.getClassRoomId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID));
            post.setClassRoom(classRoom);
        }

        UserResponse user = userService.getCurrentUser();

        if(user.getRole()== Role.LEADER) {
            if(!user.getId().equals(post.getClassRoom().getLeaderId()) ) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }
        postMapper.updateEntity(postRequest, post);
        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponse deletePost(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID));

        UserResponse user = userService.getCurrentUser();

        if(user.getRole()== Role.LEADER) {
            if(!user.getId().equals(post.getClassRoom().getLeaderId()) ) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }
        postRepository.delete(post);
        return postMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<PostResponse> getPostsOfClass(String classId, PaginationRequestDto requestDto) {

        if(!classroomRepository.existsById(classId)){
                throw new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND_ID);
        }

        UserResponse currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ADMIN) {
            if ( !classroomRepository.existsByIdAndMembers_Id(classId, currentUser.getId())) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
        }

        Pageable pageable = PaginationUtil.buildPageable(requestDto);

        Page<Post> postPage = postRepository.findByClassRoomId(classId, pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(postPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, postPage);
        return new PaginationResponseDto<>(meta, postResponseList);
    }

    @Override
    public PaginationResponseDto<PostResponse> getAllPosts() {
        return null;
    }


}
