package com.eduvault.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private Double amount;
    private String periodType;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private Boolean isPaid;
    private LocalDate paidDate;
    private String paymentMethod;
    private String notes;
    private LocalDateTime createdAt;
}