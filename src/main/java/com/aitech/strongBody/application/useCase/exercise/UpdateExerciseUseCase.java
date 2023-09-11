package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.rest.dto.exercise.UpdateExerciseDto;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseUseCase {
    private final ExerciseRepository repository;

    public UpdateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(UpdateExerciseDto input, UUID id) {
        this.getExerciseById(id);
        var updatedExercise = Exercise.builder()
            .id(id)
            .name(input.name())
            .description(input.description())
            .level(input.level())
            .type(input.type())
            .equipment(input.equipment())
            .imageUrl(input.imageUrl())
            .videoUrl(input.videoUrl())
            .build();
        this.repository.update(updatedExercise);
    }

    private Exercise getExerciseById(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
        return foundExercise.get();
    }
}
