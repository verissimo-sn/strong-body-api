package com.aitech.strongBody.domain.repository;

import com.aitech.strongBody.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void create(User user);
    Optional<User> getById(UUID id);
    void update(User user);
    UserDetails getByEmail(String email);
    void deleteAll();
}
