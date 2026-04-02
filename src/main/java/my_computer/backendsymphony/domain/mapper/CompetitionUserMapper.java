package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.CompetitionUserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionMemberResponse;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionUserResponse toResponse(CompetitionUser competitionUser);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.intake", target = "intake")
    @Mapping(source = "user.imageUrl", target = "imageUrl")
    @Mapping(source = "user.studentCode", target = "studentCode")
    @Mapping(source = "joinedAt", target = "joinedAt")
    CompetitionMemberResponse toCompetitionMemberResponse(CompetitionUser competitionUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompetitionUser(@MappingTarget CompetitionUser competitionUser, CompetitionUserUpdateRequest request);

    List<CompetitionUserResponse> toResponseList(List<CompetitionUser> competitionUserList);

    List<CompetitionMemberResponse> toCompetitionMemberResponseList(List<CompetitionUser> competitionUsers);
}
