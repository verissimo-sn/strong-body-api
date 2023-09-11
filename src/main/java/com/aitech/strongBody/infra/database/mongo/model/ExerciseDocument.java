package com.aitech.strongBody.infra.database.mongo.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "exercises")
public class ExerciseDocument {
    @Id
    private UUID id;
    private String name;
    private String description;
    private String level;
    private String type;
    private String equipment;
    private String imageUrl;
    private String videoUrl;
}
