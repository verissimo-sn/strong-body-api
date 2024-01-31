package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class CreateExerciseUseCase {
    @Autowired
    private final ExerciseRepository repository;

    public UUID execute(Exercise input) {
        this.repository.create(input);
        log.info("execute::Id: {}", input.getId().toString());
        return input.getId();
    }
}
