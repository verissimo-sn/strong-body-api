package com.aitech.strongBody.useCase.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.aitech.strongBody.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.exception.NotFoundException;
import com.aitech.strongBody.infra.database.ExerciseRepository;
import com.aitech.strongBody.infra.database.model.ExerciseDocument;

@Tag("Unit")
@SpringBootTest
@DisplayName("UpdateExerciseUseCase")
public class UpdateExerciseUseCaseTests {
    UpdateExerciseDto fakeExerciseInputDto;
    ExerciseDocument expectedExerciseOutput;

    @InjectMocks
    private UpdateExerciseUseCase updateExerciseUseCase;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void buildSetUp() {
        this.fakeExerciseInputDto = new UpdateExerciseDto(
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
        when(this.exerciseRepository.findById(this.expectedExerciseOutput.getId())).thenReturn(Optional.of(this.expectedExerciseOutput));
    }

    @Test
    @DisplayName("Should update exercise with correct params")
    void shouldUpdateAnExercise() {
        this.updateExerciseUseCase.execute(this.fakeExerciseInputDto, this.expectedExerciseOutput.getId());
        verify(this.exerciseRepository).save(this.expectedExerciseOutput);
    }

    @Test
    @DisplayName("Should throw exception when exercise not found")
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(this.exerciseRepository.findById(this.expectedExerciseOutput.getId())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> {
            this.updateExerciseUseCase.execute(this.fakeExerciseInputDto, this.expectedExerciseOutput.getId());
        });

        assertEquals("Exercise not found", exception.getMessage());
    }
}
