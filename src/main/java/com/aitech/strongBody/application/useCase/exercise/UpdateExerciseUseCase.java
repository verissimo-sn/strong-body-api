package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateExerciseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateExerciseUseCase.class);
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
                input.getVideoUrl());
        this.repository.update(exercise);
        logger.info("execute::input: {}", input.toString());
    }

    private Exercise getExerciseById(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            logger.error("getExerciseById::Id: {}::Exercise not found", id);
            throw new NotFoundException("Exercise not found");
        }
        return foundExercise.get();
    }
}
