package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] CreateExerciseUseCase")
public class CreateExerciseUseCaseTests {
    Exercise exercise;

    @InjectMocks
    private CreateExerciseUseCase createExerciseUseCase;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void setUp() {
        this.exercise = Exercise.builder()
                .id(UUID.randomUUID())
                .name("name")
                .description("description")
                .level("level")
                .type("type")
                .equipment("equipment")
                .imageUrl("imageUrl")
                .videoUrl("videoUrl")
                .build();
    }

    @Test
    @DisplayName("Should create exercise with params and return id")
    void createAnExerciseCorrectly() {
        var id = this.createExerciseUseCase.execute(this.exercise);
        verify(this.repository).create(this.exercise);
        assertEquals(this.exercise.getId(), id);
    }
}
