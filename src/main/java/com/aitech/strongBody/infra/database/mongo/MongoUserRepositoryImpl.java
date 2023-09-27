package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.database.mongo.model.UserDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoUserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(MongoUserRepositoryImpl.class);
    private final SpringDataMongoUserRepository repository;

    public MongoUserRepositoryImpl(SpringDataMongoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(User user) {
        logger.info("create::User: {}", user.toString());
        this.repository.save(this.fromEntityToDocument(user));
    }

    @Override
    public Optional<User> getById(UUID id) {
        var foundExercise = this.repository.findById(id);
        logger.info("getById::Id: {}::User: {}", id, foundExercise.toString());
        return foundExercise.map(this::fromDocumentToEntity);
    }


    @Override
    public void update(User user) {
        logger.info("update::User: {}", user.toString());
        this.repository.save(this.fromEntityToDocument(user));
    }


    private User fromDocumentToEntity(UserDocument document) {
        return User
                .builder()
                .id(document.getId())
                .name(document.getName())
                .email(document.getEmail())
                .nickname(document.getNickname())
                .avatarUrl(document.getAvatarUrl())
                .build();
    }

    private UserDocument fromEntityToDocument(User entity) {
        var userDocument = new UserDocument();
        userDocument.setId(entity.getId());
        userDocument.setName(entity.getName());
        userDocument.setEmail(entity.getEmail());
        userDocument.setNickname(entity.getNickname());
        userDocument.setAvatarUrl(entity.getAvatarUrl());
        return userDocument;
    }
}
