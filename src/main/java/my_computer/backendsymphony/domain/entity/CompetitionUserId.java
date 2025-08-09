package my_computer.backendsymphony.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CompetitionUserId implements Serializable {

    private String competition;
    private String user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionUserId)) return false;
        CompetitionUserId that = (CompetitionUserId) o;
        return Objects.equals(competition, that.competition) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition, user);
    }
}
