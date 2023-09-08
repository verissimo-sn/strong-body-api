package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.entity.ExerciseDocument;
import com.aitech.strongBody.exception.NotFoundException;
import com.aitech.strongBody.repository.JpaExerciseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetExerciseByIdUseCase {
    @Autowired
    private JpaExerciseRepositoryImpl exerciseRepository;

    public ExerciseDocument execute(String id) {
        var foundExercise = this.exerciseRepository.findById(id);
        if (foundExercise.isEmpty()) {
            throw new NotFoundException("Exercise not found");
        }
        return foundExercise.get();
    }
}
