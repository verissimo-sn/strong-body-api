package com.aitech.strongBody.application.useCase.exercise;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;

@Service
public class GetExerciseByIdUseCase {
    private final ExerciseRepository repository;

    public GetExerciseByIdUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public Exercise execute(UUID id) {
        var foundExercise = this.repository.getById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
        return foundExercise.get();
    }
}
