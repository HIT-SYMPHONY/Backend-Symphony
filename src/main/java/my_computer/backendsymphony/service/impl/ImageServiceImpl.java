package my_computer.backendsymphony.service.impl;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.domain.dto.response.ImageUploadResponse;
import my_computer.backendsymphony.service.ImageService;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    UploadFileUtil uploadFileUtil;

    @Override
    public ImageUploadResponse uploadImage(MultipartFile imageFile) {
        String imageUrl = uploadFileUtil.uploadImage(imageFile);
        return ImageUploadResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }
}
