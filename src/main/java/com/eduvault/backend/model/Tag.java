package com.eduvault.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name ;

    @Column(unique = true)
    private String slug;

    @PrePersist     //runs automatically before saving to DB
    public void generateSlug(){     // convert tag name to url friendly format
        this.slug=this.name.toLowerCase().trim().replaceAll("\\s+","-");
    }
}
