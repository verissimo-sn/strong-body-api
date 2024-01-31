package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class GetExerciseListUseCase {
    
    @Autowired
    private final ExerciseRepository repository;

    public Page<Exercise> execute(Pageable pageable) {
        log.info("execute::Pageable: {}", pageable.toString());
        return this.repository.getAll(pageable);
    }
}
