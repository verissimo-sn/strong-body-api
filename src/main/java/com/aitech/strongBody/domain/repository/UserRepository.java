package com.aitech.strongBody.domain.repository;

import com.aitech.strongBody.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void create(User user);
    Optional<User> getById(UUID id);
    void update(User user);
    Optional<User> getByEmail(String email);
    void deleteAll();
}
