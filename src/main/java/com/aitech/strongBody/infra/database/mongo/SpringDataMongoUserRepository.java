package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.infra.database.mongo.model.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<UserDocument, UUID> {
}