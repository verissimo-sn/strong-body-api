package com.aitech.strongBody.domain.entity;

import java.util.UUID;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BaseEntity {
    private final UUID id;

    public BaseEntity() {
        this.id = UUID.randomUUID();
    }

    public BaseEntity(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
