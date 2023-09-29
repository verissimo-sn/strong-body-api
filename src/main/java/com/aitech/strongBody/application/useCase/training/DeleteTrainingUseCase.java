package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteTrainingUseCase {
    private static final Logger logger = LoggerFactory.getLogger(DeleteTrainingUseCase.class);

    @Autowired
    private final TrainingRepository repository;

    public void execute(UUID id) {
        this.getTrainingById(id);
        this.repository.deleteById(id);
        logger.info("execute::Id: {}", id);
    }

    private void getTrainingById(UUID id) {
        var foundTraining = this.repository.getById(id);
        if (foundTraining.isEmpty()) {
            logger.error("getTrainingById::Id: {}::Training not found", id);
            throw new NotFoundException("Training not found");
        }
    }
}
