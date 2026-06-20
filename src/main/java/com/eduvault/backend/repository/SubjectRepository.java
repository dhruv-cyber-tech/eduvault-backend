package com.eduvault.backend.repository;

import com.eduvault.backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findByStandardId(Long standardId);
    boolean existsByNameAndStandardId(String name,Long standardId);
}
