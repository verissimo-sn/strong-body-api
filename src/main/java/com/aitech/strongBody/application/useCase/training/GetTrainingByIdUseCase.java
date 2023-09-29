package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetTrainingByIdUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetTrainingByIdUseCase.class);

    @Autowired
    private final TrainingRepository trainingRepository;

    public Training execute(UUID id) {
        var foundTraining = this.trainingRepository.getById(id);
        if (foundTraining.isEmpty()) {
            logger.error("execute::Id: {}::Training not found", id);
            throw new NotFoundException("Training not found");
        }
        return foundTraining.get();
    }
}
