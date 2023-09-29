package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateTrainingUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateTrainingUseCase.class);

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final UserRepository userRepository;

    public void execute(Training input) {
        this.getUserById(input.getUserId());
        this.trainingRepository.create(input);
        logger.info("execute::input: {}", input.toString());
    }

    private void getUserById(UUID id) {
        var foundUser = this.userRepository.getById(id);
        if (foundUser.isEmpty()) {
            logger.error("getUserById::Id: {}::User not found", id);
            throw new NotFoundException("User not found");
        }
    }
}
