package com.eduvault.backend.controller;

import com.eduvault.backend.dto.request.CreateChapterRequest;
import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Chapter;
import com.eduvault.backend.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chapters")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<Chapter>>> getChaptersBySubject(
            @PathVariable Long subjectId) {
        List<Chapter> chapters = chapterService.getChaptersBySubject(subjectId);
        return ResponseEntity.ok(
                ApiResponse.success(chapters, "Chapters fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Chapter>> createChapter(
            @RequestBody CreateChapterRequest request) {
        Chapter chapter = chapterService.createChapter(
                request.getName(),
                request.getChapterNumber(),
                request.getSubjectId());
        return ResponseEntity.ok(
                ApiResponse.success(chapter, "Chapter created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChapter(
            @PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Chapter deleted successfully"));
    }
}