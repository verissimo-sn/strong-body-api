package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.infra.database.h2.model.UserH2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataH2UserRepository extends JpaRepository<UserH2, UUID> {
    public Optional<UserH2> findByEmail(String email);

}
