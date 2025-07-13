package my_computer.backendsymphony.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum SortByDataConstant  {
    COMPETITION(
            Set.of("startTime", "endTime", "createdAt"),
            "startTime"
    );

    private final Set<String> allowedFields;
    private final String defaultSortField;

    public String getSortBy(String sortBy) {
        if (sortBy != null && this.allowedFields.contains(sortBy)) {
            return sortBy;
        }
        return this.defaultSortField;
    }
}
