package com.eduvault.backend.repository;

import com.eduvault.backend.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long> {
    @Query("""
        SELECT r FROM Resource r
        WHERE (:standardId IS NULL OR r.standard.id = :standardId)
        AND (:subjectId IS NULL OR r.subject.id = :subjectId)
        AND (:chapterId IS NULL OR r.chapter.id = :chapterId)
        AND (:resourceType IS NULL OR r.resourceType = :resourceType)
        AND (:search IS NULL OR
             LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
             LOWER(r.description) LIKE LOWER(CONCAT('%', :search, '%')))
        ORDER BY r.createdAt DESC
    """)
    Page<Resource> findWithFilters(
            @Param("standardId") Long standardId, //@Param is simply the bridge between your Java method parameters and the named placeholders inside the JPQL query.
            @Param("subjectId") Long subjectId,
            @Param("chapterId") Long chapterId,
            @Param("resourceType") Resource.ResourceType resourceType,
            @Param("search") String search,
            Pageable pageable
    );
    @Query("SELECT r FROM Resource r ORDER BY r.createdAt DESC")
    List<Resource> findRecentUploads(Pageable pageable);

    long countByStandardId(Long standardId);
    long countBySubjectId(Long subjectId);
    long countByResourceType(Resource.ResourceType resourceType);

}
