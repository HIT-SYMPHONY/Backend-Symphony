package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomResponse toClassroomResponse(ClassRoom classroom);
    ClassRoom toClassRoom(ClassroomCreationRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClassroom(ClassroomUpdateRequest request, @MappingTarget ClassRoom classroom);
}
