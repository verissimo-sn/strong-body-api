package com.aitech.strongBody.infra.rest.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String nickname,
        @NotBlank String avatarUrl,
        @NotBlank String password) {
}
