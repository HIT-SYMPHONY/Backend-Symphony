package my_computer.backendsymphony.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionDetailResponse extends CompetitionResponse{
    String content;
}
