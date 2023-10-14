package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.NotFoundException;
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
public class UpdateUserUseCase {

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
        log.info("execute::input: {}", input.toString());
    }

    private User getUserById(UUID id) {
         return this.repository.getById(id).orElseThrow(() -> {
            log.error("getUserById::Id: {}::User not found", id);
            return new NotFoundException("User not found");
        });
    }
}
