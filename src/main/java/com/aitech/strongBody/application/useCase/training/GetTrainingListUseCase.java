package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTrainingListUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetTrainingListUseCase.class);

    @Autowired
    private final TrainingRepository repository;

    public Page<Training> execute(Pageable pageable) {
        logger.info("execute::Pageable: {}", pageable.toString());
        return this.repository.getAll(pageable);
    }
}
