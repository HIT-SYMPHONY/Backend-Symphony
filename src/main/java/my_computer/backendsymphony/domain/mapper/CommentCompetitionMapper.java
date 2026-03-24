package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.MarkRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentCompetitionMapper {

    CommentCompetition toEntity(CommentCompetitionRequest request);

    CommentCompetitionResponse toResponse(CommentCompetition commentCompetition);

    List<CommentCompetitionResponse> toListResponse(List<CommentCompetition> listCommentCompetition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentCompetitionFromMark(MarkRequest request, @MappingTarget CommentCompetition commentCompetition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentCompetitionFromContent(CommentCompetitionUpdateRequest request, @MappingTarget CommentCompetition commentCompetition);
}
