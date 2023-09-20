package com.aitech.strongBody.infra.database.h2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "exercises")
public class ExerciseH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String level;
    private String type;
    private String equipment;
    private String imageUrl;
    private String videoUrl;
}
