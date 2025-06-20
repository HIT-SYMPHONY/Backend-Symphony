package my_computer.backendsymphony.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.exception.UploadFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class UploadFileUtil {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new UploadFileException("File is empty!");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new UploadFileException("Invalid file type. Only image files are allowed.");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("resource_type", "image")
            );
            Object url = result.get("secure_url");
            if (url == null) {
                throw new UploadFileException("Upload failed: No URL returned from Cloudinary.");
            }

            return url.toString();

        } catch (IOException e) {
            throw new UploadFileException("Upload file failed!");
        }
    }

    public void destroyFileWithUrl(String url) {
        try {
            String publicId = extractPublicIdFromUrl(url);
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Destroy image public id {} result: {}", publicId, result);
        } catch (IOException e) {
            throw new UploadFileException("Remove file failed!");
        }
    }

    private String extractPublicIdFromUrl(String url) {
        // Ví dụ: https://res.cloudinary.com/<cloud_name>/image/upload/v1710000000/folder/image.name.v1.png
        int uploadIndex = url.indexOf("/upload/");
        if (uploadIndex == -1) {
            throw new UploadFileException("Invalid Cloudinary URL");
        }

        // Lấy phần sau "/upload/"
        String afterUpload = url.substring(uploadIndex + "/upload/".length());

        // Xoá phần version nếu có (vd: v1710000000/)
        if (afterUpload.startsWith("v") && afterUpload.contains("/")) {
            afterUpload = afterUpload.substring(afterUpload.indexOf("/") + 1);
        }

        // Xoá phần đuôi file (vd: .jpg, .png, ...)
        int lastDotIndex = afterUpload.lastIndexOf(".");
        if (lastDotIndex != -1) {
            afterUpload = afterUpload.substring(0, lastDotIndex);
        }

        return afterUpload;
    }


}
