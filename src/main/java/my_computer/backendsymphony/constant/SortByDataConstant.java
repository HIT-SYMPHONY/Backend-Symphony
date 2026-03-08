package my_computer.backendsymphony.constant;

import lombok.Getter;

import java.util.Optional;
import java.util.Set;

@Getter
public enum SortByDataConstant {
    USER(
            Set.of("createdAt", "intake")
    ),

    COMPETITION(
            Set.of("startTime", "endTime", "createdAt"),
            "startTime"
    ),
    CLASSROOM(
            Set.of("startTime", "endTime")
    ),
    POST(
            Set.of("createdAt", "updatedAt", "deadline"),
            "deadline"
    ),
    LESSON(
            Set.of("createdAt")
    ),
    NOTIFICATION(
            Set.of("createdAt"),
            "createdAt"
    ),
    COMMENT_POST(
            Set.of("createdAt")
    )
    ;

    private final Set<String> allowedFields;
    private final String defaultSortField;

    SortByDataConstant(Set<String> allowedFields, String defaultSortField) {
        this.allowedFields = allowedFields;
        this.defaultSortField = defaultSortField;
    }

    SortByDataConstant(Set<String> allowedFields) {
        this.allowedFields = allowedFields;
        this.defaultSortField = null;
    }

    public Optional<String> resolveSortField(String clientSortBy) {
        if (clientSortBy != null && !clientSortBy.isBlank()) {
            if (this.allowedFields.contains(clientSortBy)) {
                return Optional.of(clientSortBy);
            } else {
                throw new IllegalArgumentException(
                        String.format(ErrorMessage.INVALID_SORT_FIELD, clientSortBy, this.allowedFields)
                );
            }
        }
        return Optional.ofNullable(this.defaultSortField);
    }
}
