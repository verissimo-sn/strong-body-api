package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateUserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserUseCase.class);

    @Autowired
    private final UserRepository repository;

    public void execute(User input) {
        var user = this.getUserById(input.getId());
        user.update(
                input.getName(),
                input.getEmail(),
                input.getNickname(),
                input.getAvatarUrl());
        this.repository.update(user);
        logger.info("execute::input: {}", input.toString());
    }

    private User getUserById(UUID id) {
        var foundUser = this.repository.getById(id);
        if (foundUser.isEmpty()) {
            logger.error("getUserById::Id: {}::User not found", id);
            throw new NotFoundException("User not found");
        }
        return foundUser.get();
    }
}
