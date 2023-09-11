package com.aitech.strongBody.useCase.exercise;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.aitech.strongBody.application.useCase.exercise.CreateExerciseUseCase;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;

@Tag("Unit")
@SpringBootTest
@DisplayName("CreateExerciseUseCase")
public class CreateExerciseUseCaseTests {
    CreateExerciseDto exerciseInputDto;
    Exercise expectedExerciseOutput;

    @InjectMocks
    private CreateExerciseUseCase createExerciseUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        this.exerciseInputDto = new CreateExerciseDto(
                "name",
                "description",
                "level",
                "type",
                "equipment",
                "imageUrl",
                "videoUrl"
        );
        this.expectedExerciseOutput = Exercise.builder()
                .name(this.exerciseInputDto.name())
                .description(this.exerciseInputDto.description())
                .level(this.exerciseInputDto.level())
                .type(this.exerciseInputDto.type())
                .equipment(this.exerciseInputDto.equipment())
                .imageUrl(this.exerciseInputDto.imageUrl())
                .videoUrl(this.exerciseInputDto.videoUrl())
                .build();
        when(this.exerciseRepository.create(this.expectedExerciseOutput)).thenReturn(null);
    }

    @Test
    @DisplayName("Should create exercise with correct params")
    void shouldCreateAnExercise() {
        this.createExerciseUseCase.execute(this.fakeExerciseInputDto);
        verify(this.exerciseRepository).create(this.expectedExerciseOutput);
    }
}
