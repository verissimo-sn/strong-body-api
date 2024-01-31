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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] GetExerciseByIdUseCase")
public class GetExerciseByIdUseCaseTests {
    private final UUID EXERCISE_ID = UUID.randomUUID();

    @InjectMocks
    private GetExerciseByIdUseCase getExerciseByIdUseCase;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void buildSetUp() {
        var fakeExercise = Exercise.builder().id(EXERCISE_ID).build();
       when(this.repository.getById(EXERCISE_ID)).thenReturn(Optional.of(fakeExercise));
    }

    @Test
    @DisplayName("Should find exercise by id and return it")
    void findExerciseById() {
        var exercise = this.getExerciseByIdUseCase.execute(EXERCISE_ID);

        assertEquals(EXERCISE_ID, exercise.getId());
        assertInstanceOf(Exercise.class, exercise);
    }

    @Test
    @DisplayName("Should throw exception when exercise not found")
    void throwExceptionWhenExerciseNotFound() {
        when(this.repository.getById(EXERCISE_ID)).thenReturn(Optional.empty());
        var anyId = UUID.randomUUID();
        var exception = assertThrows(NotFoundException.class, () -> {
            this.getExerciseByIdUseCase.execute(anyId);
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
