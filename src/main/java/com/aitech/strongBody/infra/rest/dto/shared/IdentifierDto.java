package com.aitech.strongBody.infra.rest.dto.shared;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record IdentifierDto(@NotBlank UUID id) {
}
