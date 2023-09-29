package com.aitech.strongBody.infra.database.mongo.model;

import com.aitech.strongBody.domain.enums.TrainingStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "trainings")
public class TrainingDocument {
    @Id
    private UUID id;
    private UUID userId;
    private String name;
    private String level;
    private TrainingStatus status;
    private int requiredSessions = 0;
    private int finishedSessions = 0;
}
