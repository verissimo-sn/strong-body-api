package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.infra.database.h2.model.TrainingH2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataH2TrainingRepository extends JpaRepository<TrainingH2, UUID> {
    Page<TrainingH2> findByNameLikeOrderByNameAsc(String name, Pageable pageable);
}