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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] GetExerciseListUseCase")
public class GetExerciseListUseCaseTests {
    final Pageable pageInput = Pageable.ofSize(1).withPage(0);
    final Page<Exercise> fakeExercisePage =  new PageImpl<>(List.of(new Exercise()));

    @InjectMocks
    private GetExerciseListUseCase getExerciseListUseCase;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void buildSetUp() {
       when(this.repository.getAll(pageInput)).thenReturn(this.fakeExercisePage);
    }

    @Test
    @DisplayName("Should find and paginate exercises")
    void findExercise() {
        var exercise = this.getExerciseListUseCase.execute(pageInput);
        var content = exercise.getContent();
        assertInstanceOf(Page.class, exercise);
        assertInstanceOf(Exercise.class, content.get(0));
        assertEquals(1, exercise.getTotalElements());
    }
}
