package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class CreateTrainingUseCase {

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final UserRepository userRepository;

    public UUID execute(Training input) {
        this.getUserById(input.getUserId());
        this.trainingRepository.create(input);
        log.info("execute::Id: {}", input.getId().toString());
        return input.getId();
    }

    private void getUserById(UUID id) {
        this.userRepository.getById(id).orElseThrow(() -> {
            log.error("getUserById::Id: {}::User not found", id);
            return new NotFoundException("User not found");
        });
    }
}
