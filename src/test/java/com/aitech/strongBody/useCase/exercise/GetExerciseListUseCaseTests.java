package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.document.ExerciseDocument;
import com.aitech.strongBody.repository.ExerciseRepository;
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
    final Pageable withPage = Pageable.ofSize(1).withPage(0);

    @InjectMocks
    private GetExerciseListUseCase getExerciseListUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        var fakeExercise = new ExerciseDocument();
       when(this.exerciseRepository.findAll(withPage)).thenReturn(new PageImpl<>(List.of(fakeExercise)));
    }

    @Test
    @DisplayName("Should find and paginate exercises")
    void shouldFindExercise() {
        var exercise = this.getExerciseListUseCase.execute(withPage);
        assertInstanceOf(Page.class, exercise);
        assertEquals(1, exercise.getTotalElements());
    }
}
