package my_computer.backendsymphony.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.CompetitionUserStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionUserUpdateRequest {
    CompetitionUserStatus status;
}
