package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);
    private final UserRepository repository;

    public CreateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(User input) {
        this.repository.create(input);
        logger.info("execute::input: {}", input.toString());
    }
}
