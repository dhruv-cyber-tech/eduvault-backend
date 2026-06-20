package com.eduvault.backend.repository;

import com.eduvault.backend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    List<Chapter> findBySubjectId(Long subjectId);
}
