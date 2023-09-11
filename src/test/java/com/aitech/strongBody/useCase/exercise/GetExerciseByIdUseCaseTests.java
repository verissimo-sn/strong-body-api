package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.application.useCase.exercise.GetExerciseByIdUseCase;
import com.aitech.strongBody.infra.database.ExerciseRepository;
import com.aitech.strongBody.infra.database.model.ExerciseDocument;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("getExerciseByIdUseCass")
public class GetExerciseByIdUseCaseTests {
    private final String EXERCISE_ID = "validId";

    @InjectMocks
    private GetExerciseByIdUseCase getExerciseByIdUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        var fakeExercise = new ExerciseDocument();
        fakeExercise.setId(EXERCISE_ID);
       when(this.exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.of(fakeExercise));
    }

    @Test
    @DisplayName("Should find exercise by id and return it")
    void shouldFindExerciseById() {
        var exercise = this.getExerciseByIdUseCase.execute(EXERCISE_ID);

        assertEquals(EXERCISE_ID, exercise.getId());
        assertInstanceOf(ExerciseDocument.class, exercise);
    }

    @Test
    @DisplayName("Should throw exception when exercise not found")
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(this.exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.getExerciseByIdUseCase.execute("anyId");
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
