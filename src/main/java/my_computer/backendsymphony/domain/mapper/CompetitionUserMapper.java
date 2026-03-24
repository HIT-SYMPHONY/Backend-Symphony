package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CompetitionUserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionUserResponse toResponse(CompetitionUser competitionUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompetitionUser(@MappingTarget CompetitionUser competitionUser, CompetitionUserUpdateRequest request);

    List<CompetitionUserResponse> toResponseList(List<CompetitionUser> competitionUserList);
}
