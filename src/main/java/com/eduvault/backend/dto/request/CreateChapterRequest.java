package com.eduvault.backend.dto.request;

import lombok.Data;

@Data
public class CreateChapterRequest {
    private String name;
    private Integer chapterNumber;
    private Long subjectId;
}