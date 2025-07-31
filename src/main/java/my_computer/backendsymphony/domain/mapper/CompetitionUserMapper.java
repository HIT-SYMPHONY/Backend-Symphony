package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionUserResponse toResponse(CompetitionUser competitionUser);

    List<CompetitionUserResponse> toResponseList(List<CompetitionUser> competitionUserList);
}
