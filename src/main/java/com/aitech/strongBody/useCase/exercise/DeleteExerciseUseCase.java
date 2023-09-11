package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.exception.NotFoundException;
import com.aitech.strongBody.infra.database.ExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteExerciseUseCase {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public void execute(String id) {
        this.getExerciseById(id);
        this.exerciseRepository.deleteById(id);
    }

    private void getExerciseById(String id) {
        var foundExercise = this.exerciseRepository.findById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
    }
}
