package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.infra.database.h2.model.ExerciseH2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataH2ExerciseRepository extends JpaRepository<ExerciseH2, UUID> {
}
