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

import com.aitech.strongBody.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.entity.ExerciseDocument;
import com.aitech.strongBody.repository.ExerciseRepository;

@Tag("Unit")
@SpringBootTest
@DisplayName("CreateExerciseUseCase")
public class CreateExerciseUseCaseTests {
    CreateExerciseDto fakeExerciseInputDto;
    ExerciseDocument expectedExerciseOutput;

    @InjectMocks
    private CreateExerciseUseCase createExerciseUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        this.fakeExerciseInputDto = new CreateExerciseDto(
                "name",
                "description",
                "level",
                "type",
                "equipment",
                "imageUrl",
                "videoUrl"
        );
        this.expectedExerciseOutput = new ExerciseDocument();
        this.expectedExerciseOutput.setName(this.fakeExerciseInputDto.name());
        this.expectedExerciseOutput.setDescription(this.fakeExerciseInputDto.description());
        this.expectedExerciseOutput.setLevel(this.fakeExerciseInputDto.level());
        this.expectedExerciseOutput.setType(this.fakeExerciseInputDto.type());
        this.expectedExerciseOutput.setEquipment(this.fakeExerciseInputDto.equipment());
        this.expectedExerciseOutput.setImageUrl(this.fakeExerciseInputDto.imageUrl());
        this.expectedExerciseOutput.setVideoUrl(this.fakeExerciseInputDto.videoUrl());
        
        when(this.exerciseRepository.insert(this.expectedExerciseOutput)).thenReturn(null);
    }

    @Test
    @DisplayName("Should create exercise with correct params")
    void shouldCreateAnExercise() {
        this.createExerciseUseCase.execute(this.fakeExerciseInputDto);
        verify(this.exerciseRepository).insert(this.expectedExerciseOutput);
    }
}
