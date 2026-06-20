package com.eduvault.backend.controller;

import com.eduvault.backend.dto.request.CreateStandardRequest;
import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.model.Standard;
import com.eduvault.backend.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/standards")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StandardController {

    private final StandardService standardService;

    // GET /api/standards
    @GetMapping
    public ResponseEntity<ApiResponse<List<Standard>>> getAllStandards() {
        List<Standard> standards = standardService.getAllStandards();
        return ResponseEntity.ok(
                ApiResponse.success(standards, "Standards fetched successfully"));
    }

    // POST /api/standards
    // CORRECT - expects JSON body
    @PostMapping
    public ResponseEntity<ApiResponse<Standard>> createStandard(
            @RequestBody CreateStandardRequest request) {
        Standard standard = standardService.createStandard(
                request.getName(), request.getSortOrder());
        return ResponseEntity.ok(
                ApiResponse.success(standard, "Standard created successfully"));
    }

    // DELETE /api/standards/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStandard(@PathVariable Long id) {
        standardService.deleteStandard(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Standard deleted successfully"));
    }
}