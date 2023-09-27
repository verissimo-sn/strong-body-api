package com.aitech.strongBody.infra.rest.dto.user;

import com.mongodb.lang.Nullable;

public record UpdateUserDto(
        @Nullable String name,
        @Nullable String nickname,
        @Nullable String avatarUrl) {
}
