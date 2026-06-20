package com.eduvault.backend.controller;

import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.dto.response.DashboardStatsDto;
import com.eduvault.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getStats() {
        DashboardStatsDto stats = dashboardService.getStats();
        return ResponseEntity.ok(
                ApiResponse.success(stats, "Dashboard stats fetched successfully"));
    }
}