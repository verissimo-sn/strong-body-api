package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class UpdateTrainingUseCase {

    @Autowired
    private final TrainingRepository repository;

    public void execute(Training input) {
        var training = this.getTrainingById(input.getId());
        if(!Objects.equals(training.getUserId().toString(), input.getUserId().toString())) {
            String errorMessage = "Provided userId: " + input.getUserId().toString() +" does not match with training userId:" + training.getUserId().toString();
            log.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }
        training.update(
                input.getName(),
                input.getLevel());
        this.repository.update(training);
        log.info("execute::input: {}", input.toString());
    }

    private Training getTrainingById(UUID id) {
        return this.repository.getById(id).orElseThrow(() -> {
            log.error("getTrainingById::Id: {}::Training not found", id);
            return new NotFoundException("Training not found");
        });
    }
}
