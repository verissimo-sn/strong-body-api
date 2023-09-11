package com.aitech.strongBody.infra.rest.dto.exercise;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CreateExerciseDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String level,
        @NotBlank
        String type,
        @NotBlank
        String equipment,
        @Nullable
        String imageUrl,
        @Nullable
        String videoUrl
) { }
