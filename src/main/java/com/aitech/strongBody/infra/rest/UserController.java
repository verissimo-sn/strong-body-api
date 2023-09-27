package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.user.CreateUserUseCase;
import com.aitech.strongBody.application.useCase.user.GetUserByIdUseCase;
import com.aitech.strongBody.application.useCase.user.UpdateUserUseCase;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.infra.rest.dto.user.CreateUserDto;
import com.aitech.strongBody.infra.rest.dto.user.UpdateUserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    CreateUserUseCase createUserUseCase;
    @Autowired
    GetUserByIdUseCase getUserByIdUseCase;
    @Autowired
    UpdateUserUseCase updateUserUseCase;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundUser = this.getUserByIdUseCase.execute(id);
        logger.info("getUserById::Id: {}", id);
        return foundUser;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createUser(@RequestBody @Valid CreateUserDto input) {
        User user = new User(
                input.name(),
                input.email(),
                input.nickname(),
                input.avatarUrl(),
                input.password());
        this.createUserUseCase.execute(user);
        logger.info("createUser::User: {}", user.toString());
        return user.getId();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @RequestBody @Valid UpdateUserDto input,
            @PathVariable(value = "id") UUID id) {
        User user = User.builder()
                .id(id)
                .name(input.name())
                .nickname(input.nickname())
                .avatarUrl(input.avatarUrl())
                .build();
        this.updateUserUseCase.execute(user);
        logger.info("updateUser::User: {}", user.toString());
    }
}
