package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.repository.ExerciseRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class DeleteExerciseUseCase {
    private final ExerciseRepository repository;

    public DeleteExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(UUID id) {
        this.getExerciseById(id);
        this.repository.deleteById(id);
    }

    private void getExerciseById(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
    }
}
