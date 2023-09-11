package com.aitech.strongBody.application.useCase.exercise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;

@Service
public class GetExerciseListUseCase {
    private final ExerciseRepository repository;

    public GetExerciseListUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public Page<Exercise> execute(Pageable pageable) {
        return this.repository.getAll(pageable);
    }
}
