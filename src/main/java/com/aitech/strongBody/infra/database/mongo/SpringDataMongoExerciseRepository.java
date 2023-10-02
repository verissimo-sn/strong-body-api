package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMongoExerciseRepository extends MongoRepository<ExerciseDocument, UUID> {
    @Query(value = "{ '_id' : { $in: ?0 } }")
    List<ExerciseDocument> findByIds(UUID[] ids);
}