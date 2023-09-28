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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] DeleteExerciseUseCase")
public class DeleteExerciseUseCaseTests {
    private final UUID EXERCISE_ID = UUID.randomUUID();

    @InjectMocks
    private DeleteExerciseUseCase deleteExerciseUseCase;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void setUp() {
        var exercise = Exercise.builder().id(EXERCISE_ID).build();
        when(this.repository.getById(EXERCISE_ID)).thenReturn(Optional.of(exercise));
    }

    @Test
    @DisplayName("Should delete exercise by id")
    void deleteExerciseById() {
        this.deleteExerciseUseCase.execute(EXERCISE_ID);
        verify(this.repository).deleteById(EXERCISE_ID);
    }

    @Test
    @DisplayName("Should throw exception when exercise not exists")
    void throwExceptionWhenExerciseNotFound() {
        when(this.repository.getById(EXERCISE_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.deleteExerciseUseCase.execute(EXERCISE_ID);
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
