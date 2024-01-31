package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
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
public class UpdateExerciseUseCase {

    @Autowired
    private final ExerciseRepository repository;

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
        log.info("execute::input: {}", input.toString());
    }

    private Exercise getExerciseById(UUID id) {
        return this.repository.getById(id).orElseThrow(() -> {
            log.error("getExerciseById::Id: {}::Exercise not found", id);
            return new NotFoundException("Exercise not found");
        });
    }
}
