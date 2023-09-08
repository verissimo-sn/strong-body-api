package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.entity.ExerciseDocument;
import com.aitech.strongBody.repository.JpaExerciseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetExerciseListUseCase {
    @Autowired
    private JpaExerciseRepositoryImpl exerciseRepository;

    public Page<ExerciseDocument> execute(Pageable pageable) {
        return this.exerciseRepository.findAll(pageable);
    }
}
