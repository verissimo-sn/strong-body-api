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
public class FilterTrainingListByNameUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FilterTrainingListByNameUseCase.class);

    @Autowired
    private final TrainingRepository repository;

    public Page<Training> execute(String name, Pageable pageable) {
        logger.info("execute::Name: {}::Pageable: {}", name, pageable.toString());
        return this.repository.filterByName(name, pageable);
    }
}
