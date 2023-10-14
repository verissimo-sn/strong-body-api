package com.aitech.strongBody.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
@ToString
public class User extends Entity {
    private String name;
    private String email;
    private String nickname;
    private String avatarUrl;
    private String password;

    public void update(
            final String name,
            final String email,
            final String nickname,
            final String avatarUrl) {
        this.setName(name == null ? this.name : name);
        this.setEmail(email == null ? this.email : email);
        this.setNickname(nickname == null ? this.nickname : nickname);
        this.setAvatarUrl(avatarUrl == null ? this.avatarUrl : avatarUrl);
    }

    public void changePassword(final String password) {
        this.setPassword(password);
    }
}
