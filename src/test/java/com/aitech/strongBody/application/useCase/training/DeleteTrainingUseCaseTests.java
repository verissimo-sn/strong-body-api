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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] DeleteTrainingUseCase")
public class DeleteTrainingUseCaseTests {
    private final UUID TRAINING_ID = UUID.randomUUID();

    @InjectMocks
    private DeleteTrainingUseCase deleteTrainingUseCase;

    @Mock
    private TrainingRepository trainingRepository;

    @BeforeEach
    void setUp() {
        var training = Training.builder()
                .id(TRAINING_ID).userId(UUID.randomUUID())
                .name("training name")
                .level("training level")
                .build();
        when(this.trainingRepository.getById(training.getId())).thenReturn(Optional.of(training));
    }

    @Test
    @DisplayName("Should delete training by id")
    void deleteTrainingById() {
        this.deleteTrainingUseCase.execute(TRAINING_ID);
        verify(this.trainingRepository).deleteById(TRAINING_ID);
    }

    @Test
    @DisplayName("Should throw exception when training not exists")
    void throwExceptionWhenTrainingNotFound() {
        when(this.trainingRepository.getById(TRAINING_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.deleteTrainingUseCase.execute(TRAINING_ID);
        });

        assertEquals("Training not found", exception.getMessage());
    }
}
