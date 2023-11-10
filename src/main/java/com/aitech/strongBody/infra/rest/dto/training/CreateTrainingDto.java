package com.aitech.strongBody.infra.rest.dto.training;

import jakarta.validation.constraints.NotNull;

public record CreateTrainingDto(
        @NotNull String name,
        @NotNull String level
) {
}
