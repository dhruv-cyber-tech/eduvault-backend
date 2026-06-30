package com.eduvault.backend.repository;

import com.eduvault.backend.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @EntityGraph(attributePaths = "standard")
    List<Student> findByIsActive(Boolean isActive);

    @EntityGraph(attributePaths = "standard")
    List<Student> findByStandardIdAndIsActive(Long standardId, Boolean isActive);

    @Override
    @EntityGraph(attributePaths = "standard")
    Optional<Student> findById(Long id);

    boolean existsByParentContact(String parentContact);

    long countByIsActive(Boolean isActive);

    long countByStandardId(Long standardId);
}