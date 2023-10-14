package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class CreateUserUseCase {

    @Autowired
    private final UserRepository repository;

    public UUID execute(User input) {
        var mail = input.getEmail().toLowerCase();
        this.mailAlreadyRegistered(mail);
        input.setEmail(mail);
        this.repository.create(input);
        log.info("execute::Id: {}", input.getId().toString());
        return input.getId();
    }

    private void mailAlreadyRegistered(String email) {
        this.repository.getByEmail(email).ifPresent(user -> {
            log.error("mailAlreadyRegistered::Email: {}::User email already in use", user.getEmail());
            throw new BadRequestException("User email already in use");
        });
    }
}
