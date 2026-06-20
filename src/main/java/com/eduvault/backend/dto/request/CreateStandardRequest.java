package com.eduvault.backend.dto.request;

import lombok.Data;

@Data
public class CreateStandardRequest {
    private String name;
    private Integer sortOrder;
}