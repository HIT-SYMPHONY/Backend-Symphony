package my_computer.backendsymphony.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUploadResponse {
    private String imageUrl;
}
