package com.eduvault.backend.repository;

import com.eduvault.backend.model.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends JpaRepository<Standard,Long> {
    boolean existsByName(String name);

}
