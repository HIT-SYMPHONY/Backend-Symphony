package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastLogin", ignore = true),
            @Mapping(target = "imageUrl", ignore = true),
            @Mapping(target = "chatRoomUsers", ignore = true),
            @Mapping(target = "classRooms", ignore = true),
            @Mapping(target = "fullName", expression = "java(request.getLastName() + \" \" + request.getFirstName())")
    })
    User toUser(UserCreationRequest request);

}