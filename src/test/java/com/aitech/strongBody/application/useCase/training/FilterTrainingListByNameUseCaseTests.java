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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Unit")
@SpringBootTest
@DisplayName("[UseCase] FilterTrainingListByNameUseCase")
public class FilterTrainingListByNameUseCaseTests {
    private final Training training = new Training();
    private final Pageable pageInput = Pageable.ofSize(1).withPage(0);
    private final Page<Training> trainingPage =  new PageImpl<>(List.of(training));

    @InjectMocks
    private FilterTrainingListByNameUseCase filterTrainingListByNameUseCase;

    @Mock
    private TrainingRepository repository;

    @BeforeEach
    void buildSetUp() {
       when(this.repository.filterByName(this.training.getName(), pageInput)).thenReturn(this.trainingPage);
    }

    @Test
    @DisplayName("Should find and paginate training by name")
    void findTrainingByName() {
        var trainings = this.filterTrainingListByNameUseCase.execute(this.training.getName(), pageInput);
        var content = trainings.getContent();
        verify(this.repository).filterByName(this.training.getName(), pageInput);
        assertInstanceOf(Page.class, trainings);
        assertInstanceOf(Training.class, content.get(0));
        assertEquals(1, trainings.getTotalElements());
    }
}
