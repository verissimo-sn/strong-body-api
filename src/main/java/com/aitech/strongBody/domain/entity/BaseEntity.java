package com.aitech.strongBody.domain.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
public class BaseEntity {
    private final UUID id;

    public BaseEntity() {
        this.id = UUID.randomUUID();
    }

    public BaseEntity(final UUID id) {
        this.id = id;
    }
}
