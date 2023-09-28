package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    @Autowired
    private final UserRepository repository;

    public void execute(User input) {
        var mail = input.getEmail().toLowerCase();
        this.mailAlreadyRegistered(mail);
        input.setEmail(mail);
        this.repository.create(input);
        logger.info("execute::input: {}", input.toString());
    }

    private void mailAlreadyRegistered(String email) {
        var foundUser = this.repository.getByEmail(email);
        if (foundUser.isPresent()) {
            logger.error("getUserByEmail::User email already in use");
            throw new BadRequestException("User email already in use");
        }
    }
}
