package com.aitech.strongBody.repository;

import com.aitech.strongBody.document.ExerciseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends MongoRepository<ExerciseDocument, String> {
}
