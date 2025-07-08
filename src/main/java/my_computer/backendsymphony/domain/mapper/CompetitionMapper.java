package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CompetitionCreationRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    Competition toCompetition(CompetitionCreationRequest request);

    CompetitionResponse toCompetitionResponse(Competition competition);

    List<CompetitionResponse> toCompetitionResponseList(List<Competition> competitions);

}
