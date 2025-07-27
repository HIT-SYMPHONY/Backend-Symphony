package my_computer.backendsymphony.domain.mapper;

import jakarta.validation.Valid;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentCompetitionMapper {

    CommentCompetition toEntity(CommentCompetitionRequest request);

    @Mapping(source = "competition.name", target = "competitionName")
    CommentCompetitionResponse toResponse(CommentCompetition commentCompetition);

    List<CommentCompetitionResponse> toListResponse(List<CommentCompetition> listCommentCompetition);

}
