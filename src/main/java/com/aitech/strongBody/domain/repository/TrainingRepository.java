package com.aitech.strongBody.domain.repository;

import com.aitech.strongBody.domain.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository {
    void create(Training training);
    Optional<Training> getById(UUID id);
    Page<Training> filterByName(String name, Pageable pageable);
    Page<Training> getAll(Pageable pageable);
    void update(Training exercise);
    void deleteById(UUID id);
    void deleteAll();
}
