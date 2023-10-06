package com.aitech.strongBody.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class BaseEntity {
    private UUID id;

    public BaseEntity() {
        this.id = UUID.randomUUID();
    }
}
