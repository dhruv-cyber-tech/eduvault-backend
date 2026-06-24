package com.eduvault.backend.controller;

import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Resource;
import com.eduvault.backend.service.FileStorageService;
import com.eduvault.backend.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*") // Fixed for Vercel!
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;
    private final FileStorageService fileStorageService;

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
            Principal principal) throws IOException { // Replaced adminUserId with Principal

        // Pass principal.getName() (which is the user's email) to the service
        Resource resource = resourceService.uploadResource(
                title, description, resourceType,
                standardId, subjectId, chapterId,
                tags, file, principal.getName());
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
    public ResponseEntity<Void> downloadResource(
            @PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        String url = fileStorageService.getDownloadUrl(resource.getFilePath());
        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<Void> previewResource(
            @PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        String url = fileStorageService.getDownloadUrl(resource.getFilePath());
        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }
}