package com.aitech.strongBody.infra.database.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "users")
public class UserDocument {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String nickname;
    private String avatarUrl;
    private String password;
}
