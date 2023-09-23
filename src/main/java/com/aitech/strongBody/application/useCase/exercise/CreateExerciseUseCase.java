package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateExerciseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateExerciseUseCase.class);
    private final ExerciseRepository repository;

    public CreateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(Exercise input) {
        this.repository.create(input);
        logger.info("execute::input: {}", input.toString());
    }
}
