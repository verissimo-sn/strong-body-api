package com.aitech.strongBody.infra.rest.dto.training;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTrainingDto(
        @NotNull UUID userId,
        @NotNull String name,
        @NotNull String level
) {
}
