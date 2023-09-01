package com.aitech.strongBody.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
