package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    Competition toCompetition(CompetitionRequest request);

    CompetitionResponse toCompetitionResponse(Competition competition);

    List<CompetitionResponse> toCompetitionResponseList(List<Competition> competitions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompetition(CompetitionRequest request, @MappingTarget Competition competition);

}
