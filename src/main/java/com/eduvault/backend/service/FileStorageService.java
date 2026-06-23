package com.eduvault.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final Cloudinary cloudinary;

    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();

        // Ensure we safely get the extension (defaulting to .pdf if missing)
        String extension = ".pdf";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // BUG FIX: Attach the extension to the public_id!
        String uniqueFileName = "eduvault/resources/" + UUID.randomUUID() + extension;

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", uniqueFileName, // Now this ends in .pdf
                        "resource_type", "raw"
                        // You can safely remove "format", "pdf"
                )
        );

        return uploadResult.get("secure_url").toString();
    }

    public String getDownloadUrl(String fileUrl) {
        // fileUrl is already the full Cloudinary URL
        return fileUrl;
    }

    public void deleteFile(String fileUrl) throws IOException {
        // extract public_id from URL
        String publicId = extractPublicId(fileUrl);
        cloudinary.uploader().destroy(publicId,
                ObjectUtils.asMap("resource_type", "raw"));
    }

    private String extractPublicId(String fileUrl) {
        // URL format: https://res.cloudinary.com/cloud/raw/upload/v123/eduvault/resources/uuid
        String[] parts = fileUrl.split("/upload/");
        if (parts.length > 1) {
            String afterUpload = parts[1];
            // remove version prefix like v1234567890/
            afterUpload = afterUpload.replaceAll("^v\\d+/", "");
            return afterUpload;
        }
        return fileUrl;
    }

    public java.nio.file.Path getFilePath(String fileUrl) {
        // not used with Cloudinary but kept for compatibility
        return java.nio.file.Paths.get(fileUrl);
    }
}