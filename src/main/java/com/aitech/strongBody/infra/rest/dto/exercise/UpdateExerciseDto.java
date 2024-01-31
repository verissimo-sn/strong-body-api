package com.aitech.strongBody.infra.rest.dto.exercise;

import com.mongodb.lang.Nullable;

public record UpdateExerciseDto(
        @Nullable String name,
        @Nullable String description,
        @Nullable String level,
        @Nullable String type,
        @Nullable String equipment,
        @Nullable String imageUrl,
        @Nullable String videoUrl) {
}
