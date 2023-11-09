package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.BadRequestException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] CreateUserUseCase")
public class CreateUserUseCaseTests {
    User user;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.user = User.builder()
                .id(UUID.randomUUID())
                .name("name")
                .email("email")
                .nickname("nickName")
                .avatarUrl("avatarUrl")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("Should create user with correctly params")
    void createAnExerciseCorrectly() {
        this.createUserUseCase.execute(this.user);
        verify(this.userRepository).create(this.user);
    }

    @Test
    @DisplayName("Should throw exception when email already in use")
    void throwExceptionWhenEmailAlreadyInUse() {
        when(this.userRepository.getByEmail(this.user.getEmail())).thenReturn(this.user);
        var exception = assertThrows(BadRequestException.class, () -> {
            this.createUserUseCase.execute(this.user);
        });
        assertEquals("User email already in use", exception.getMessage());
    }
}
