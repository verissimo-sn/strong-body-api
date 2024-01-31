package com.aitech.strongBody.infra.database.mongo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class TrainingGroupDocument{
    private String tag;
    private String description;
    private int order;
    @DBRef(lazy = true)
    private List<ExerciseDocument> exercises;
}
