package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class GetTrainingByIdUseCase {

    @Autowired
    private final TrainingRepository trainingRepository;

    public Training execute(UUID id) {
        return this.trainingRepository.getById(id).orElseThrow(() -> {
            log.error("execute::Id: {}::Training not found", id);
            return new NotFoundException("Training not found");
        });
    }
}
