package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.entity.ExerciseDocument;
import com.aitech.strongBody.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateExerciseUseCase {
    @Autowired
    ExerciseRepository exerciseRepository;

    public void execute(CreateExerciseDto input) {
        this.exerciseRepository.insert(this.dtoToDocument(input));
    }

    private ExerciseDocument dtoToDocument(CreateExerciseDto dto) {
        var newExercise = new ExerciseDocument();
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
