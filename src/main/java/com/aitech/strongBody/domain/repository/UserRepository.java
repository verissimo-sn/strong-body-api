package com.aitech.strongBody.domain.repository;

import com.aitech.strongBody.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void create(User user);

    Optional<User> getById(UUID id);

    Page<User> getAll(Pageable pageable);

    void update(User user);

    void deleteById(UUID id);
}
