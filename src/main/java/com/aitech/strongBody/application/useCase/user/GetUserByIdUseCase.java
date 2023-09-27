package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserByIdUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetUserByIdUseCase.class);
    private final UserRepository repository;

    public GetUserByIdUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public User execute(UUID id) {
        var foundUser = this.repository.getById(id);
        if (foundUser.isEmpty()) {
            logger.error("execute::Id: {}::User not found", id);
            throw new NotFoundException("User not found");
        }
        var user = foundUser.get();
        logger.info("execute::Exercise: {}::", user.toString());
        return user;
    }
}
