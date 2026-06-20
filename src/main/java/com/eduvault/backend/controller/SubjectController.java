package com.eduvault.backend.controller;

import com.eduvault.backend.dto.request.CreateSubjectRequest;
import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Subject;
import com.eduvault.backend.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/by-standard/{standardId}")
    public ResponseEntity<ApiResponse<List<Subject>>> getSubjectsByStandard(
            @PathVariable Long standardId) {
        List<Subject> subjects = subjectService.getSubjectsByStandard(standardId);
        return ResponseEntity.ok(
                ApiResponse.success(subjects, "Subjects fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Subject>> createSubject(
            @RequestBody CreateSubjectRequest request) {
        Subject subject = subjectService.createSubject(
                request.getName(), request.getStandardId());
        return ResponseEntity.ok(
                ApiResponse.success(subject, "Subject created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(
            @PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Subject deleted successfully"));
    }
}