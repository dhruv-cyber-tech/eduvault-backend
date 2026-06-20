package com.eduvault.backend.controller;

import com.eduvault.backend.dto.request.CreateTagRequest;
import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Tag;
import com.eduvault.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(
                ApiResponse.success(tags, "Tags fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tag>> createTag(
            @RequestBody CreateTagRequest request) {
        Tag tag = tagService.createTag(request.getName());
        return ResponseEntity.ok(
                ApiResponse.success(tag, "Tag created successfully"));
    }
}