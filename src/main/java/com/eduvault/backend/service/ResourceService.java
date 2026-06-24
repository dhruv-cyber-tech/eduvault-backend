package com.eduvault.backend.service;

import com.eduvault.backend.model.*;
import com.eduvault.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;
    private final ChapterRepository chapterRepository;
    private final TagRepository tagRepository;
    private final AdminUserRepository adminUserRepository;
    private final FileStorageService fileStorageService;

    // get all resources with filters and pagination
    public Page<Resource> getResources(
            Long standardId, Long subjectId, Long chapterId,
            String resourceType, String search,
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        // convert string to enum if provided
        Resource.ResourceType type = null;
        if (resourceType != null && !resourceType.isEmpty()) {
            type = Resource.ResourceType.valueOf(resourceType);
        }

        return resourceRepository.findWithFilters(
                standardId, subjectId, chapterId,
                type, search, pageable);
    }

    // get single resource by id
    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    // upload new resource
    public Resource uploadResource(
            String title, String description,
            String resourceType, Long standardId,
            Long subjectId, Long chapterId,
            List<String> tagNames,
            MultipartFile file,
            String adminEmail) throws IOException { // Changed to String adminEmail

        // verify admin exists using the email from the secure JWT token
        AdminUser adminUser = adminUserRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // verify standard exists
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        // verify subject exists
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // save file to disk first
        String savedFileName = fileStorageService.saveFile(file);

        // build the resource object
        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setResourceType(Resource.ResourceType.valueOf(resourceType));
        resource.setStandard(standard);
        resource.setSubject(subject);
        resource.setFileName(file.getOriginalFilename());
        resource.setFilePath(savedFileName);
        resource.setFileSize(file.getSize());
        resource.setMimeType(file.getContentType());
        resource.setUploadedBy(adminUser);

        // chapter is optional
        if (chapterId != null) {
            Chapter chapter = chapterRepository.findById(chapterId)
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));
            resource.setChapter(chapter);
        }

        // handle tags
        if (tagNames != null && !tagNames.isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : tagNames) {
                // if tag exists use it, otherwise create new
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                tags.add(tag);
            }
            resource.setTags(tags);
        }

        return resourceRepository.save(resource);
    }

    // delete resource and its file
    public void deleteResource(Long id) throws IOException {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        // delete file from disk first
        fileStorageService.deleteFile(resource.getFilePath());

        // then delete from database
        resourceRepository.deleteById(id);
    }

    // get last 5 uploads for dashboard
    public List<Resource> getRecentUploads() {
        return resourceRepository.findRecentUploads(
                PageRequest.of(0, 5));
    }

    public Path getFilePath(String fileName) {
        return fileStorageService.getFilePath(fileName);
    }
}