package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.document.ExerciseDocument;
import com.aitech.strongBody.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetExerciseByIdUseCase {
    @Autowired
    ExerciseRepository exerciseRepository;

    public Optional<ExerciseDocument> execute(String id) {
        this.getExerciseById(id);
        return this.exerciseRepository.findById(id);
    }

    private void getExerciseById(String id) {
        var foundExercise = this.exerciseRepository.findById(id);
        if (foundExercise.isEmpty()) {
            throw new RuntimeException("Exercise not found");
        }
    }
}
