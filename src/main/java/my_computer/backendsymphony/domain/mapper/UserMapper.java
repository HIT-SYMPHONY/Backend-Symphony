package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.UserCreationRequest;
import my_computer.backendsymphony.domain.dto.request.UserUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    List<UserResponse> toListUserResponse(List<User> users);

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

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastLogin", ignore = true),
            @Mapping(target = "imageUrl", ignore = true),
            @Mapping(target = "chatRoomUsers", ignore = true),
            @Mapping(target = "classRooms", ignore = true),
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUser(UserUpdateRequest request, @MappingTarget User user);

}