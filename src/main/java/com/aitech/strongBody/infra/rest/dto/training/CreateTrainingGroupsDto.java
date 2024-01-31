package com.aitech.strongBody.infra.rest.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateTrainingGroupsDto(
        @NotBlank String tag,
        @NotBlank String description,
        @NotNull int order,
        List<String> exercisesId) {
}
