package com.eduvault.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecentUploadDto {
    private Long id;
    private String title;
    private String subjectName;
    private String standardName;
    private String resourceType;
    private LocalDateTime createdAt;
}