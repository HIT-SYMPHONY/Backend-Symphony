package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentCompetitionMapper {

    @Mapping(source = "competition.id", target = "competitionId")
    CommentCompetitionResponse toResponse(CommentCompetition commentCompetition);

    CommentCompetition toEntity(CommentCompetitionRequest commentCompetitionRequest);
}
