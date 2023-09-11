package com.aitech.strongBody.infra.database.mongo;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;

@Repository
public interface SpringDataMongoExerciseRepository extends MongoRepository<ExerciseDocument, UUID> {
}