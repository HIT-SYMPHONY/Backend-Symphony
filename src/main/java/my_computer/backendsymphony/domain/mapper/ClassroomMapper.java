package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomResponse toClassroomResponse(ClassRoom user);
    ClassRoom toClassRoom(ClassroomCreationRequest request);
}
