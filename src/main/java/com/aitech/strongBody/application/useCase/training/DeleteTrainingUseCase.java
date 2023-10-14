package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class DeleteTrainingUseCase {

    @Autowired
    private final TrainingRepository repository;

    public void execute(UUID id) {
        this.getTrainingById(id);
        this.repository.deleteById(id);
        log.info("execute::Id: {}", id);
    }

    private void getTrainingById(UUID id) {
        var foundTraining = this.repository.getById(id).orElseThrow(() -> {
            log.error("getTrainingById::Id: {}::Training not found", id);
            return new NotFoundException("Training not found");
        });
    }
}
