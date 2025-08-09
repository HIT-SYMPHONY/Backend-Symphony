package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CommentPostRequest;
import my_computer.backendsymphony.domain.dto.response.CommentPostResponse;
import my_computer.backendsymphony.domain.entity.CommentPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentPostMapper {

    @Mapping(target = "post", ignore = true)
    CommentPost toEntity(CommentPostRequest commentPostRequest);

    @Mapping(source = "post.id", target = "postId")
    CommentPostResponse toResponse(CommentPost commentPost);
}
