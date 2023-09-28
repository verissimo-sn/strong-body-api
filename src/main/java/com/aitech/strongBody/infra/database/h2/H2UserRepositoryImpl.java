package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.database.h2.model.UserH2;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class H2UserRepositoryImpl implements UserRepository {
    private final SpringDataH2UserRepository springDataH2UserRepository;

    public H2UserRepositoryImpl(SpringDataH2UserRepository springDataH2ExerciseRepository) {
        this.springDataH2UserRepository = springDataH2ExerciseRepository;
    }

    @Override
    public void create(User user) {
        this.springDataH2UserRepository.save(toH2Entity(user));
    }

    @Override
    public Optional<User> getById(UUID id) {
        var foundUser = this.springDataH2UserRepository.findById(id);
        return foundUser.map(this::toEntity);
    }

    @Override
    public void update(User user) {
        this.springDataH2UserRepository.save(toH2Entity(user));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        var foundUser = this.springDataH2UserRepository.findByEmail(email);
        return foundUser.map(this::toEntity);
    }

    private User toEntity(UserH2 user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .password(user.getPassword())
                .build();
    }

    private UserH2 toH2Entity(User entity) {
        UserH2 userH2 = new UserH2();
        userH2.setId(entity.getId());
        userH2.setName(entity.getName());
        userH2.setNickname(entity.getNickname());
        userH2.setEmail(entity.getEmail());
        userH2.setAvatarUrl(entity.getAvatarUrl());
        userH2.setPassword(entity.getPassword());
        return userH2;
    }
}
