package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.application.useCase.exercise.DeleteExerciseUseCase;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("DeleteExerciseUseCase")
public class DeleteExerciseUseCaseTests {
    private final UUID EXERCISE_ID = UUID.randomUUID();

    @InjectMocks
    private DeleteExerciseUseCase deleteExerciseUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        var fakeExercise = new Exercise();
        fakeExercise.setId(EXERCISE_ID);
        when(this.exerciseRepository.getById(EXERCISE_ID)).thenReturn(Optional.of(fakeExercise));
    }

    @Test
    @DisplayName("Should delete exercise by id")
    void shouldDeleteExerciseById() {
        this.deleteExerciseUseCase.execute(EXERCISE_ID);
        verify(this.exerciseRepository).deleteById(EXERCISE_ID);
    }

    @Test
    @DisplayName("Should throw exception when exercise not found")
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(this.exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.deleteExerciseUseCase.execute("anyId");
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
