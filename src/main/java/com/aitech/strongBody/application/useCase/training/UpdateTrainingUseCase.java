package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateTrainingUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateTrainingUseCase.class);

    @Autowired
    private final TrainingRepository repository;

    public void execute(Training input) {
        var training = this.getTrainingById(input.getId());
        if(!Objects.equals(training.getUserId().toString(), input.getUserId().toString())) {
            String errorMessage = "Provided userId: " + input.getUserId().toString() +" does not match with training userId:" + training.getUserId().toString();
            logger.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }
        training.update(
                input.getName(),
                input.getLevel());
        this.repository.update(training);
        logger.info("execute::input: {}", input.toString());
    }

    private Training getTrainingById(UUID id) {
        var foundTraining = this.repository.getById(id);
        if (foundTraining.isEmpty()) {
            logger.error("getTrainingById::Id: {}::Training not found", id);
            throw new NotFoundException("Training not found");
        }
        return foundTraining.get();
    }
}
