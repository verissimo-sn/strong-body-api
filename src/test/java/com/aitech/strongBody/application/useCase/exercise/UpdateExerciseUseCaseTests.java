package com.aitech.strongBody.application.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
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
@DisplayName("[UseCase] UpdateExerciseUseCase")
public class UpdateExerciseUseCaseTests {
    Exercise exercise;

    @InjectMocks
    private UpdateExerciseUseCase updateExerciseUseCase;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void buildSetUp() {
        this.exercise = Exercise.builder()
                .name("name")
                .description("description")
                .level("level")
                .type("type")
                .equipment("equipment")
                .imageUrl("imageUrl")
                .videoUrl("videoUrl")
                .build();
        when(this.repository.getById(this.exercise.getId())).thenReturn(Optional.of(this.exercise));
    }

    @Test
    @DisplayName("Should update exercise with correct params")
    void updateAnExercise() {
        this.updateExerciseUseCase.execute(this.exercise);
        verify(this.repository).update(this.exercise);
    }

    @Test
    @DisplayName("Should throw exception when exercise not found")
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(this.repository.getById(this.exercise.getId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.updateExerciseUseCase.execute(this.exercise);
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
