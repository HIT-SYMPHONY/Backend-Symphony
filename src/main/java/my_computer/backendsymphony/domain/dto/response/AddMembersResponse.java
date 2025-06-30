package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddMembersResponse {
    String classroomId;
    int newlyAddedCount;
    List<String> addedMemberIds;
    List<String> alreadyMemberIds;
}
