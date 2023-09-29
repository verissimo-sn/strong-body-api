package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
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
@DisplayName("[UseCase] GetTrainingByIdUseCase")
public class GetTrainingByIdUseCaseTests {
    private final UUID TRAINING_ID = UUID.randomUUID();

    @InjectMocks
    private GetTrainingByIdUseCase getTrainingByIdUseCase;

    @Mock
    private TrainingRepository repository;

    @BeforeEach
    void buildSetUp() {
        var training = Training.builder()
                .id(TRAINING_ID)
                .userId(UUID.randomUUID())
                .name("name")
                .level("level")
                .build();
       when(this.repository.getById(TRAINING_ID)).thenReturn(Optional.of(training));
    }

    @Test
    @DisplayName("Should find training by id and return it")
    void findTrainingById() {
        var training = this.getTrainingByIdUseCase.execute(TRAINING_ID);

        assertEquals(TRAINING_ID, training.getId());
        assertInstanceOf(Training.class, training);
    }

    @Test
    @DisplayName("Should throw exception when training not found")
    void throwExceptionWhenTrainingNotFound() {
        when(this.repository.getById(TRAINING_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.getTrainingByIdUseCase.execute(UUID.randomUUID());
        });

        assertEquals("Training not found", exception.getMessage());
    }
}
