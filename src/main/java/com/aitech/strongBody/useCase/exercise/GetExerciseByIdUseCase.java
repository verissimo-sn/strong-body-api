package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.document.ExerciseDocument;
import com.aitech.strongBody.exception.NotFoundException;
import com.aitech.strongBody.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetExerciseByIdUseCase {
    @Autowired
    ExerciseRepository exerciseRepository;

    public ExerciseDocument execute(String id) {
        this.getExerciseById(id);
        return this.exerciseRepository.findById(id).get();
    }

    private void getExerciseById(String id) {
        var foundExercise = this.exerciseRepository.findById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
    }
}
