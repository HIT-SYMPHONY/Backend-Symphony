package my_computer.backendsymphony.service;

import my_computer.backendsymphony.domain.dto.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageUploadResponse uploadImage(MultipartFile imageFile);
}
