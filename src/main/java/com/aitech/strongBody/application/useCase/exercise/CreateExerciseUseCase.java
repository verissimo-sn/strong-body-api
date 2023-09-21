package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateExerciseUseCase {
    private final ExerciseRepository repository;

    public CreateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(Exercise input) {
        this.repository.create(input);
    }
}
