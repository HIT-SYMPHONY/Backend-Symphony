package my_computer.backendsymphony.domain.mapper;

import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.dto.response.ClassroomSummaryResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomResponse toClassroomResponse(ClassRoom classroom);

    @Mapping(target = "numberOfPosts", ignore = true)
    @Mapping(target = "leaderName", ignore = true)
    ClassroomSummaryResponse toClassroomSummaryResponse(ClassRoom classroom);

    ClassRoom toClassRoom(ClassroomCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClassroom(ClassroomUpdateRequest request, @MappingTarget ClassRoom classroom);

    List<ClassroomResponse> toClassroomResponseList(List<ClassRoom> classRooms);

    default List<ClassroomSummaryResponse> toClassroomSummaryResponseList(List<ClassRoom> classRooms, Map<String, String> leaderNames, Map<String, Long> postCounts) {
        if (classRooms == null) return null;
        return classRooms.stream().map(classroom -> {
            ClassroomSummaryResponse response = toClassroomSummaryResponse(classroom);
            if (leaderNames != null) {
                response.setLeaderName(leaderNames.get(classroom.getLeaderId()));
            }
            if (postCounts != null) {
                Long count = postCounts.get(classroom.getId());
                response.setNumberOfPosts(count != null ? count.intValue() : 0);
            }
            return response;
        }).toList();
    }
}
