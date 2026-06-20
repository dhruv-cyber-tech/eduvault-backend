package com.eduvault.backend.controller;

import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Resource;
import com.eduvault.backend.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // GET /api/resources?standardId=1&subjectId=2&search=algebra
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Resource>>> getResources(
            @RequestParam(required = false) Long standardId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long chapterId,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Resource> resources = resourceService.getResources(
                standardId, subjectId, chapterId, resourceType, search, page, size);
        return ResponseEntity.ok(
                ApiResponse.success(resources, "Resources fetched successfully"));
    }

    // GET /api/resources/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Resource>> getResourceById(
            @PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(
                ApiResponse.success(resource, "Resource fetched successfully"));
    }

    // POST /api/resources (multipart/form-data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Resource>> uploadResource(
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam String resourceType,
            @RequestParam Long standardId,
            @RequestParam Long subjectId,
            @RequestParam(required = false) Long chapterId,
            @RequestParam(required = false) List<String> tags,
            @RequestParam MultipartFile file,
            @RequestParam Long adminUserId) throws IOException {

        Resource resource = resourceService.uploadResource(
                title, description, resourceType,
                standardId, subjectId, chapterId,
                tags, file, adminUserId);
        return ResponseEntity.ok(
                ApiResponse.success(resource, "Resource uploaded successfully"));
    }

    // DELETE /api/resources/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteResource(
            @PathVariable Long id) throws IOException {
        resourceService.deleteResource(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Resource deleted successfully"));
    }

    // GET /api/resources/1/download
    @GetMapping("/{id}/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadResource(
            @PathVariable Long id) throws MalformedURLException {

        Resource resource = resourceService.getResourceById(id);
        Path filePath = resourceService.getFilePath(resource.getFilePath());

        org.springframework.core.io.Resource fileResource =
                new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFileName() + "\"")
                .body(fileResource);
    }

    // GET /api/resources/1/preview
    @GetMapping("/{id}/preview")
    public ResponseEntity<org.springframework.core.io.Resource> previewResource(
            @PathVariable Long id) throws MalformedURLException {

        Resource resource = resourceService.getResourceById(id);
        Path filePath = resourceService.getFilePath(resource.getFilePath());

        org.springframework.core.io.Resource fileResource =
                new UrlResource(filePath.toUri());

        // inline = opens in browser
        // attachment = downloads the file
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFileName() + "\"")
                .body(fileResource);
    }
}