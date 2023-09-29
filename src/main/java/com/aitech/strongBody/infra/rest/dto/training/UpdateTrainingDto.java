package com.aitech.strongBody.infra.rest.dto.training;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTrainingDto(
        @NotNull UUID userId,
        String name,
        String level
) {
}
