package com.aitech.strongBody.domain.entity;

import com.aitech.strongBody.domain.enums.UserRoles;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@SuperBuilder
@ToString
public class User extends Entity implements UserDetails {
    private String name;
    private String email;
    private String nickname;
    private String avatarUrl;
    private String password;
    private UserRoles role = UserRoles.USER;

    public User(
            final String name,
            final String email,
            final String nickname,
            final String avatarUrl,
            final String password,
            final UserRoles role) {
        super();
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.role = role == null ? UserRoles.USER : role;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRoles.TRAINING) return List.of(
                new SimpleGrantedAuthority("ROLE_TRAINING"), new SimpleGrantedAuthority("ROLE_USER")
        );

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
