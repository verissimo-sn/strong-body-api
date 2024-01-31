package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.database.mongo.model.UserDocument;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@AllArgsConstructor
public class MongoUserRepositoryImpl implements UserRepository {
    @Autowired
    private SpringDataMongoUserRepository repository;

    @Override
    public void create(User user) {
        log.info("create::User: {}", user.toString());
        this.repository.save(this.toDocument(user));
    }

    @Override
    public Optional<User> getById(UUID id) {
        var foundUser = this.repository.findById(id);
        log.info("getById::Id: {}::User: {}", id, foundUser.toString());
        return foundUser.map(this::toEntity);
    }

    @Override
    public UserDetails getByEmail(String email) {
        var user = this.repository.findByEmail(email);
        log.info("getByEmail::Email: {}", email);
        return user != null ? this.toEntity((UserDocument) user) : null;
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public void update(User user) {
        log.info("update::User: {}", user.toString());
        this.repository.save(this.toDocument(user));
    }

    private User toEntity(UserDocument document) {
        return User
                .builder()
                .id(document.getId())
                .name(document.getName())
                .email(document.getEmail())
                .nickname(document.getNickname())
                .avatarUrl(document.getAvatarUrl())
                .password(document.getPassword())
                .role(document.getRole())
                .build();
    }

    private UserDocument toDocument(User entity) {
        var userDocument = new UserDocument();
        userDocument.setId(entity.getId());
        userDocument.setName(entity.getName());
        userDocument.setEmail(entity.getEmail());
        userDocument.setNickname(entity.getNickname());
        userDocument.setAvatarUrl(entity.getAvatarUrl());
        userDocument.setPassword(entity.getPassword());
        userDocument.setRole(entity.getRole());
        return userDocument;
    }
}
