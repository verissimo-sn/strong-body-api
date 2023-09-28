package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetExerciseListUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetExerciseListUseCase.class);

    @Autowired
    private final ExerciseRepository repository;


    public Page<Exercise> execute(Pageable pageable) {
        logger.info("execute::Pageable: {}", pageable.toString());
        return this.repository.getAll(pageable);
    }
}
