package com.aitech.strongBody.infra.database.h2.model;

import com.aitech.strongBody.domain.enums.TrainingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "trainings")
public class TrainingH2 {
    @Id
    private UUID id;
    private UUID userId;
    private String name;
    private String level;
    private TrainingStatus status;
    private int requiredSessions = 0;
    private int finishedSessions = 0;
}
