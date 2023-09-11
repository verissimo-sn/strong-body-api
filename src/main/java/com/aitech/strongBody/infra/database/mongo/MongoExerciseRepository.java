package com.aitech.strongBody.infra.database.mongo;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aitech.strongBody.infra.database.model.ExerciseDocument;

public interface MongoExerciseRepository extends MongoRepository<ExerciseDocument, UUID> {
  
}