package com.aitech.strongBody.application.useCase.exercise;

import org.springframework.stereotype.Service;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;

@Service
public class CreateExerciseUseCase {
    private final ExerciseRepository repository;

    public CreateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    public void execute(CreateExerciseDto input) {
        this.repository.create(this.dtoToEntity(input));
    }

    private Exercise dtoToEntity(CreateExerciseDto dto) {
        var newExercise = new Exercise();
        newExercise.setName(dto.name());
        newExercise.setDescription(dto.description());
        newExercise.setLevel(dto.level());
        newExercise.setType(dto.type());
        newExercise.setEquipment(dto.equipment());
        newExercise.setImageUrl(dto.imageUrl());
        newExercise.setVideoUrl(dto.videoUrl());
        return  newExercise;
    }

}
