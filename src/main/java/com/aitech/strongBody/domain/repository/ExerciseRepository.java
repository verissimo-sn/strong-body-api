package com.aitech.strongBody.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aitech.strongBody.domain.entity.Exercise;

public interface ExerciseRepository {
  void create(Exercise exercise);
  Exercise getById(UUID id);
  Page<Exercise> getAll(Pageable pageable);
  void update(Exercise exercise);
  void delete(UUID id);
}
