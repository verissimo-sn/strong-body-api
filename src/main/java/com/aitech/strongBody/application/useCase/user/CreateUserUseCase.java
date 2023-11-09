package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class CreateUserUseCase {

    @Autowired
    private final UserRepository repository;

    public void execute(User input) {
        var mail = input.getEmail().toLowerCase();
        this.mailAlreadyRegistered(mail);
        input.setEmail(mail);
        this.repository.create(input);
        log.info("execute::Id: {}", input.getId().toString());
    }

    private void mailAlreadyRegistered(String email) {
        var foundedUserByMail = this.repository.getByEmail(email);
        if(foundedUserByMail != null) {
            log.error("mailAlreadyRegistered::Email: {}::User email already in use", email);
            throw new BadRequestException("User email already in use");
        }
    }
}
