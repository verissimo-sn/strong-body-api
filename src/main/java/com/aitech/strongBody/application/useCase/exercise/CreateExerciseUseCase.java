package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateExerciseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateExerciseUseCase.class);

    @Autowired
    private final ExerciseRepository repository;

    public void execute(Exercise input) {
        this.repository.create(input);
        logger.info("execute::input: {}", input.toString());
    }
}
