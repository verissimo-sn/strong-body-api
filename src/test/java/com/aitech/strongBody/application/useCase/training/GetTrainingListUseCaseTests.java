package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
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
@DisplayName("[UseCase] GetTrainingListUseCase")
public class GetTrainingListUseCaseTests {
    private final Pageable pageInput = Pageable.ofSize(1).withPage(0);
    private final Page<Training> trainingPage =  new PageImpl<>(List.of(new Training()));

    @InjectMocks
    private GetTrainingListUseCase getTrainingListUseCase;

    @Mock
    private TrainingRepository repository;

    @BeforeEach
    void buildSetUp() {
       when(this.repository.getAll(pageInput)).thenReturn(this.trainingPage);
    }

    @Test
    @DisplayName("Should find and paginate training")
    void findTraining() {
        var trainings = this.getTrainingListUseCase.execute(pageInput);
        var content = trainings.getContent();
        assertInstanceOf(Page.class, trainings);
        assertInstanceOf(Training.class, content.get(0));
        assertEquals(1, trainings.getTotalElements());
    }
}
