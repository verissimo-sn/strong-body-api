package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.user.GetUserByIdUseCase;
import com.aitech.strongBody.application.useCase.user.UpdateUserUseCase;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.infra.rest.dto.user.UpdateUserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    @Autowired
    private final GetUserByIdUseCase getUserByIdUseCase;
    @Autowired
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundUser = this.getUserByIdUseCase.execute(id);
        log.info("getUserById::Id: {}", id);
        return foundUser;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
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
        log.info("updateUser::User: {}", user.toString());
    }
}
