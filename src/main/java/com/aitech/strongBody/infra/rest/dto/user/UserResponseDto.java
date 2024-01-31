package com.aitech.strongBody.infra.rest.dto.user;

import com.aitech.strongBody.domain.enums.UserRoles;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String nickname,
        String avatarUrl,
        UserRoles role
) { }
