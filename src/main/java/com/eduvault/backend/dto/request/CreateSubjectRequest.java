package com.eduvault.backend.dto.request;

import lombok.Data;

@Data
public class CreateSubjectRequest {
    private String name;
    private Long standardId;
}