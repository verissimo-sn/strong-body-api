package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.BadRequestException;
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
@DisplayName("[UseCase] UpdateTrainingUseCase")
public class UpdateTrainingUseCaseTests {
    private Training training;

    @InjectMocks
    private UpdateTrainingUseCase updateTrainingUseCase;

    @Mock
    private TrainingRepository repository;

    @BeforeEach
    void buildSetUp() {
        this.training = Training.builder()
                .userId(UUID.randomUUID())
                .name("name")
                .level("level")
                .build();
        when(this.repository.getById(this.training.getId())).thenReturn(Optional.of(this.training));
    }

    @Test
    @DisplayName("Should update training correctly")
    void updateAnTraining() {
        this.updateTrainingUseCase.execute(this.training);
        verify(this.repository).update(this.training);
    }

    @Test
    @DisplayName("Should throw notFound when training not found")
    void shouldThrowExceptionWhenTrainingNotFound() {
        when(this.repository.getById(this.training.getId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.updateTrainingUseCase.execute(this.training);
        });

        assertEquals("Training not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BadRequest when userId provided not match with actual training")
    void shouldThrowExceptionWhenProvidedUserIdNotMatchWithActualTraining() {
        var anotherTraining = Training.builder()
                .id(this.training.getId())
                .userId(UUID.randomUUID())
                .name(this.training.getName())
                .level(this.training.getLevel())
                .build();
        when(this.repository.getById(this.training.getId())).thenReturn(Optional.of(anotherTraining));
        var exception = assertThrows(BadRequestException.class, () -> {
            this.updateTrainingUseCase.execute(this.training);
        });
        String errorMessage = "Provided userId: " + this.training.getUserId().toString() +" does not match with training userId:" + anotherTraining.getUserId().toString();
        assertEquals(errorMessage, exception.getMessage());
    }
}
