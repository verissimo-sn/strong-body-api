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
public class GetExerciseByIdUseCase {

    @Autowired
    private final ExerciseRepository repository;

    public Exercise execute(UUID id) {
        var foundExercise = this.repository.getById(id).orElseThrow(() -> {
            log.error("execute::Id: {}::Exercise not found", id);
            return new NotFoundException("Exercise not found");
        });
        log.info("execute::Exercise: {}::", foundExercise.toString());
        return foundExercise;
    }
}
