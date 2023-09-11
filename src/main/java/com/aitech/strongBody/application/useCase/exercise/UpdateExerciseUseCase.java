package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseUseCase {
    private final ExerciseRepository repository;

    public UpdateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(Exercise input) {
        var exercise = this.getExerciseById(input.getId());
        exercise.update(
            input.getName(),
            input.getDescription(),
            input.getLevel(),
            input.getType(),
            input.getEquipment(),
            input.getImageUrl(),
            input.getVideoUrl()
        );
        this.repository.update(exercise);
    }

    private Exercise getExerciseById(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
        return foundExercise.get();
    }
}
