package com.aitech.strongBody.domain.repository;

import com.aitech.strongBody.domain.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository {
    void create(Exercise exercise);
    Optional<Exercise> getById(UUID id);
    List<Exercise> getByIds(UUID[] ids);
    Page<Exercise> getAll(Pageable pageable);
    void update(Exercise exercise);
    void deleteById(UUID id);
    void deleteAll();
}
