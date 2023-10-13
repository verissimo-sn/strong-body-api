package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.database.mongo.model.UserDocument;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class MongoUserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(MongoUserRepositoryImpl.class);

    @Autowired
    private SpringDataMongoUserRepository repository;

    @Override
    public void create(User user) {
        logger.info("create::User: {}", user.toString());
        this.repository.save(this.fromEntityToDocument(user));
    }

    @Override
    public Optional<User> getById(UUID id) {
        var foundUser = this.repository.findById(id);
        logger.info("getById::Id: {}::User: {}", id, foundUser.toString());
        return foundUser.map(this::fromDocumentToEntity);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        var foundEmail = this.repository.findByEmail(email);
        logger.info("getByEmail::Email: {}", email);
        return foundEmail.map(this::fromDocumentToEntity);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
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
