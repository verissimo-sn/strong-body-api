package com.aitech.strongBody.infra.rest.dto.shared;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(@NotBlank String token) {
}
