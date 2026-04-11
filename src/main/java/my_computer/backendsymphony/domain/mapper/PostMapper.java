package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;
import my_computer.backendsymphony.domain.dto.response.PostWithScoreResponse;
import my_computer.backendsymphony.domain.entity.CommentPost;
import my_computer.backendsymphony.domain.entity.Post;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;


@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PostRequest postRequest);

    @Mapping(source = "classRoom.id", target = "classRoomId")
    @Mapping(source = "classRoom.name", target = "classRoomName")
    @Mapping(target = "creatorName", ignore = true)
    PostResponse toResponse(Post post);

    @Mapping(target = "commentPostScore", ignore = true)
    @Mapping(target = "commentPostUpdatedAt", ignore = true)
    PostWithScoreResponse toPostWithScoreResponse(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePost(PostRequest request, @MappingTarget Post post);

    List<PostResponse> toResponseList(List<Post> posts);

    default List<PostWithScoreResponse> toPostWithScoreResponseList(List<Post> posts, Map<String, CommentPost> commentMap) {
        if (posts == null) return null;
        return posts.stream().map(post -> {
            PostWithScoreResponse response = toPostWithScoreResponse(post);
            CommentPost comment = commentMap.get(post.getId());
            if (comment != null) {
                response.setCommentPostScore(comment.getScore());
                response.setCommentPostUpdatedAt(comment.getUpdatedAt());
            }
            return response;
        }).toList();
    }
}
