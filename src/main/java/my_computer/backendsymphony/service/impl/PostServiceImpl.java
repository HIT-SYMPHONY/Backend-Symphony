package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;

    @Override
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
    public PaginationResponseDto<PostResponse> getPostsOfClass(String classId) {
        return null;
    }

    @Override
    public PaginationResponseDto<PostResponse> getAllPosts() {
        return null;
    }


}
