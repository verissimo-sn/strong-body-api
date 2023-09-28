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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] GetUserByIdUseCase")
public class GetUserByIdUseCaseTests {
    private final UUID USER_ID = UUID.randomUUID();

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var fakeUser = User.builder().id(USER_ID).build();
       when(this.userRepository.getById(USER_ID)).thenReturn(Optional.of(fakeUser));
    }

    @Test
    @DisplayName("Should find user by id and return it")
    void findUserById() {
        var exercise = this.getUserByIdUseCase.execute(USER_ID);

        assertEquals(USER_ID, exercise.getId());
        assertInstanceOf(User.class, exercise);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void throwExceptionWhenUserNotFound() {
        when(this.userRepository.getById(USER_ID)).thenReturn(Optional.empty());
        var anyId = UUID.randomUUID();
        var exception = assertThrows(NotFoundException.class, () -> {
            this.getUserByIdUseCase.execute(anyId);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
