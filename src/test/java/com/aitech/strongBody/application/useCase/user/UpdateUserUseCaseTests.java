package com.aitech.strongBody.application.useCase.user;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] UpdateUserUseCase")
public class UpdateUserUseCaseTests {
    User user;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void buildSetUp() {
        this.user = User.builder()
                .name("name")
                .email("mail@mail.com")
                .nickname("nick_name")
                .avatarUrl("http://avatar.com.br/")
                .password("pass")
                .build();
        when(this.userRepository.getById(this.user.getId())).thenReturn(Optional.of(this.user));
    }

    @Test
    @DisplayName("Should update user with correct params")
    void updateAnUser() {
        this.updateUserUseCase.execute(this.user);
        verify(this.userRepository).update(this.user);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(this.userRepository.getById(this.user.getId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.updateUserUseCase.execute(this.user);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
