package com.eduvault.backend.controller;

import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.dto.response.FeeDto;
import com.eduvault.backend.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fees")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<FeeDto>>> getFeesByStudent(
            @PathVariable Long studentId) {
        List<FeeDto> fees = feeService.getFeesByStudent(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(fees, "Fees fetched successfully"));
    }

    @GetMapping("/unpaid")
    public ResponseEntity<ApiResponse<List<FeeDto>>> getUnpaidFees() {
        List<FeeDto> fees = feeService.getUnpaidFees();
        return ResponseEntity.ok(
                ApiResponse.success(fees, "Unpaid fees fetched successfully"));
    }

    // POST /api/fees
    @PostMapping
    public ResponseEntity<ApiResponse<FeeDto>> createFee(
            @RequestBody Map<String, Object> request) {
        Long studentId = Long.valueOf(request.get("studentId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());
        String periodType = (String) request.get("periodType");
        LocalDate periodStart = LocalDate.parse((String) request.get("periodStart"));
        LocalDate periodEnd = LocalDate.parse((String) request.get("periodEnd"));
        String notes = (String) request.get("notes");

        FeeDto fee = feeService.createFee(studentId, amount, periodType, periodStart, periodEnd, notes);
        return ResponseEntity.ok(
                ApiResponse.success(fee, "Fee record created successfully"));
    }

    @PutMapping("/{id}/mark-paid")
    public ResponseEntity<ApiResponse<FeeDto>> markAsPaid(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        String paymentMethod = (String) request.get("paymentMethod");
        FeeDto fee = feeService.markAsPaid(id, paymentMethod);
        return ResponseEntity.ok(
                ApiResponse.success(fee, "Fee marked as paid"));
    }

    // GET /api/fees/student/1/is-paid-today
    @GetMapping("/student/{studentId}/is-paid-today")
    public ResponseEntity<ApiResponse<Boolean>> isFeePaidToday(
            @PathVariable Long studentId) {
        boolean paid = feeService.isFeePaidToday(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(paid, "Fee status checked"));
    }

    // GET /api/fees/student/1/next-due
    @GetMapping("/student/{studentId}/next-due")
    public ResponseEntity<ApiResponse<LocalDate>> getNextDueDate(
            @PathVariable Long studentId) {
        LocalDate nextDue = feeService.getNextDueDate(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(nextDue, "Next due date calculated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFee(
            @PathVariable Long id) {
        feeService.deleteFee(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Fee record deleted successfully"));
    }

    // GET /api/fees/summary?start=2026-01-01&end=2026-01-31
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getFeeSummary(
            @RequestParam String start,
            @RequestParam String end) {
        Double collected = feeService.getTotalCollectedBetween(
                LocalDate.parse(start), LocalDate.parse(end));
        Double pending = feeService.getTotalPending();
        long unpaidCount = feeService.getUnpaidCount();

        Map<String, Object> summary = Map.of(
                "totalCollected", collected,
                "totalPending", pending,
                "unpaidCount", unpaidCount
        );

        return ResponseEntity.ok(
                ApiResponse.success(summary, "Fee summary fetched successfully"));
    }
}