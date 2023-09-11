package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.infra.database.ExerciseRepository;
import com.aitech.strongBody.infra.database.model.ExerciseDocument;

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
@DisplayName("getExerciseListUseCase")
public class GetExerciseListUseCaseTests {
    final Pageable pageInput = Pageable.ofSize(1).withPage(0);
    final Page<ExerciseDocument> fakeExercisePage =  new PageImpl<>(List.of(new ExerciseDocument()));

    @InjectMocks
    private GetExerciseListUseCase getExerciseListUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
       when(this.exerciseRepository.findAll(pageInput)).thenReturn(fakeExercisePage);
    }

    @Test
    @DisplayName("Should find and paginate exercises")
    void shouldFindExercise() {
        var exercise = this.getExerciseListUseCase.execute(pageInput);
        var content = exercise.getContent();
        assertInstanceOf(Page.class, exercise);
        assertInstanceOf(ExerciseDocument.class, content.get(0));
        assertEquals(1, exercise.getTotalElements());
    }
}
