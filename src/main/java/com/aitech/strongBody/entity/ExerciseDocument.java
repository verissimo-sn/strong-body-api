package com.aitech.strongBody.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "exercises")
public class ExerciseDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private String level;
    private String type;
    private String equipment;
    private String imageUrl;
    private String videoUrl;
}
