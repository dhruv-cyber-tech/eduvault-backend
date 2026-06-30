package com.eduvault.backend.repository;

import com.eduvault.backend.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    List<Fee> findByStudentId(Long studentId);

    List<Fee> findByIsPaid(Boolean isPaid);

    // find fee periods that cover a given date
    @Query("""
        SELECT f FROM Fee f
        WHERE f.student.id = :studentId
        AND :date BETWEEN f.periodStart AND f.periodEnd
    """)
    List<Fee> findCoveringDate(@Param("studentId") Long studentId, @Param("date") LocalDate date);

    // find the latest fee period for a student (to calculate next due date)
    @Query("""
        SELECT f FROM Fee f
        WHERE f.student.id = :studentId
        ORDER BY f.periodEnd DESC
        LIMIT 1
    """)
    Fee findLatestPeriodByStudent(@Param("studentId") Long studentId);

    long countByIsPaid(Boolean isPaid);

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.isPaid = true AND f.paidDate BETWEEN :start AND :end")
    Double getTotalCollectedBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.isPaid = false")
    Double getTotalPending();
}