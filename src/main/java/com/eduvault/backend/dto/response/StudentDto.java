package com.eduvault.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String parentName;
    private String parentContact;
    private String address;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long standardId;
    private String standardName;
}