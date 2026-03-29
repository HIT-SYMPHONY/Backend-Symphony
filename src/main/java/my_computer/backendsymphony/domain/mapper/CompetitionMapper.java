package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionDetailResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionSummaryResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    Competition toCompetition(CompetitionRequest request);
    @Named("toResponse")
    CompetitionResponse toCompetitionResponse(Competition competition);

    @Named("toDetailResponse")
    CompetitionDetailResponse toCompetitionDetailResponse(Competition competition);

    @Mapping(target = "isRegistered", expression = "java(registeredIds != null && registeredIds.contains(competition.getId()))")
    CompetitionSummaryResponse toCompetitionSummaryResponse(Competition competition, @Context Set<String> registeredIds);

    List<CompetitionSummaryResponse> toCompetitionSummaryResponseList(List<Competition> competitions, @Context Set<String> registeredIds);

    @IterableMapping(qualifiedByName = "toResponse")
    List<CompetitionResponse> toCompetitionResponseList(List<Competition> competitions);

    @IterableMapping(qualifiedByName = "toDetailResponse")
    List<CompetitionDetailResponse> toCompetitionDetailResponseList(List<Competition> competitions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompetition(CompetitionRequest request, @MappingTarget Competition competition);
}