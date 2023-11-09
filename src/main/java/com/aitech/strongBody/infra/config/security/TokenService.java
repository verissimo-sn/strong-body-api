package com.aitech.strongBody.infra.config.security;

import com.aitech.strongBody.domain.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Log4j2
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer("strong-body-auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("name", user.getName())
                    .withClaim("email", user.getEmail())
                    .withClaim("nickname", user.getNickname())
                    .withClaim("avatarUrl", user.getAvatarUrl())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(this.getExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("generateToken:: Error on generate token", e);
            throw new RuntimeException("Error on generate token", e);
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
                    .withIssuer("strong-body-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            log.error("validateToken:: Error on validate token", e);
            return "";
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
