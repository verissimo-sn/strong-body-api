package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.infra.database.mongo.model.TrainingDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoTrainingRepository extends MongoRepository<TrainingDocument, UUID> {
    Page<TrainingDocument> findByNameLikeOrderByNameAsc(String name, Pageable pageable);
}