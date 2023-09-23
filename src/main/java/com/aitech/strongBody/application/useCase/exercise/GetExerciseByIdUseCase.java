package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetExerciseByIdUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetExerciseByIdUseCase.class);
    private final ExerciseRepository repository;

    public GetExerciseByIdUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public Exercise execute(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            logger.error("execute::Id: {}::Exercise not found", id);
            throw new NotFoundException("Exercise not found");
        }
        var exercise = foundExercise.get();
        logger.info("execute::Exercise: {}::", exercise.toString());
        return exercise;
    }
}
