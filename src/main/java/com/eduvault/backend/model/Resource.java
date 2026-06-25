package com.eduvault.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "file_name",nullable = false)
    private String fileName;

    @Column(name = "file_path",nullable = false)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type")
    private String mimeType;

    @Enumerated(EnumType.STRING)  // stores "WORKSHEET" as text in DB
    @Column(name ="resource_type",nullable = false)
    private ResourceType resourceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "passwordHash", "createdAt", "username", "email"})
    private AdminUser uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Standard standard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Chapter chapter;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_tag",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )

    private Set<Tag> tags = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAt(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    public enum ResourceType {
        WORKSHEET,NOTES,TEST_PAPER,SOLUTION
    }

}
