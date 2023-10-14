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
public class GetUserByIdUseCase {

    @Autowired
    private final UserRepository repository;

    public User execute(UUID id) {
        var foundUser = this.repository.getById(id).orElseThrow(() -> {
            log.error("execute::Id: {}::User not found", id);
            return new NotFoundException("User not found");
        });
        log.info("execute::User: {}::", foundUser.toString());
        return foundUser;
    }
}
