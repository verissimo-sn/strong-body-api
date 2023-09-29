package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.TrainingRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] CreateTrainingUseCase")
public class CreateTrainingUseCaseTests {
    Training training;

    @InjectMocks
    private CreateTrainingUseCase createTrainingUseCase;

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.training = Training.builder()
                .userId(UUID.randomUUID())
                .name("name")
                .level("strong")
                .build();

        var user = User.builder().id(this.training.getUserId()).build();
        when(userRepository.getById(this.training.getUserId())).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Should create training correctly")
    void createAnTrainingCorrectly() {
        this.createTrainingUseCase.execute(this.training);
        verify(this.trainingRepository).create(this.training);
    }

    @Test
    @DisplayName("Should throw notFound when provided user not exists")
    void throwNotFoundExceptionWhenUserNotExists() {
        when(userRepository.getById(this.training.getUserId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.createTrainingUseCase.execute(this.training);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
