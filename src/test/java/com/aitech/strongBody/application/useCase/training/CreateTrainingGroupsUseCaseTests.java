package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] CreateTrainingGroupsUseCase")
public class CreateTrainingGroupsUseCaseTests {
    private Training training;
    private final List<Exercise> exercises = List.of(new Exercise(), new Exercise());
    private final List<TrainingGroup> trainingGroups = List.of(
                new TrainingGroup("A", "description1", 1, exercises));

    @InjectMocks
    private CreateTrainingGroupsUseCase createTrainingGroupsUseCase;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        List<UUID> inputIds = exercises.stream().map(Exercise::getId).toList();
        this.training = Training.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .name("name")
                .level("strong")
                .trainingGroups(List.of(trainingGroups.get(0)))
                .build();
        when(trainingRepository.getById(this.training.getId())).thenReturn(Optional.of(this.training));
        when(exerciseRepository.getByIds(inputIds)).thenReturn(exercises);
    }

    @Test
    @DisplayName("Should create training groups correctly")
    void createAnTrainingGroupsCorrectly() {
        this.createTrainingGroupsUseCase.execute(this.training.getId(), this.trainingGroups);
        var experctedTraining = Training.builder()
                .id(this.training.getId())
                .userId(this.training.getUserId())
                .name(this.training.getName())
                .level(this.training.getLevel())
                .trainingGroups(this.trainingGroups)
                .build();
        verify(this.trainingRepository).update(experctedTraining);
    }

    @Test
    @DisplayName("Should throw notFound when provided training not exists")
    void throwNotFoundExceptionWhenTrainingNotExists() {
        when(trainingRepository.getById(this.training.getId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.createTrainingGroupsUseCase.execute(this.training.getId(), this.trainingGroups);
        });

        assertEquals("Training not found", exception.getMessage());
    }
}
