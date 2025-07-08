package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.PostRequest;
import my_computer.backendsymphony.domain.dto.response.PostResponse;
import my_computer.backendsymphony.domain.entity.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PostRequest postRequest);

    PostResponse toResponse(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PostRequest postRequest, @MappingTarget Post post);
}
