package com.aitech.strongBody.infra.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aitech.strongBody.infra.database.model.ExerciseDocument;

@Repository
public interface ExerciseRepository extends MongoRepository<ExerciseDocument, String> {
}
