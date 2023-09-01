package com.aitech.strongBody.useCase.exercise;

import com.aitech.strongBody.document.ExerciseDocument;
import com.aitech.strongBody.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseUseCase {

    @Autowired
    ExerciseRepository exerciseRepository;

    public void execute(UpdateExerciseDto input, String id) {
        var exercise = this.getExerciseById(id);
        this.exerciseRepository.save(this.toUpdatedDocument(input, exercise));
    }

    private ExerciseDocument getExerciseById(String id) {
        var foundExercise = this.exerciseRepository.findById(id);
        if (foundExercise.isEmpty()) {
            throw new RuntimeException("Exercise not found");
        }
        return foundExercise.get();
    }

    private ExerciseDocument toUpdatedDocument(UpdateExerciseDto dto, ExerciseDocument exercise) {
        System.out.println(dto.toString());
        exercise.setName(dto.name() == null ? exercise.getName() : dto.name());
        exercise.setDescription(dto.description() == null ? exercise.getDescription() : dto.description());
        exercise.setLevel(dto.level() == null ? exercise.getLevel() : dto.level());
        exercise.setType(dto.type() == null ? exercise.getType() : dto.type());
        exercise.setEquipment(dto.equipment() == null ? exercise.getEquipment() : dto.equipment());
        exercise.setImageUrl(dto.imageUrl() == null ? exercise.getImageUrl() : dto.imageUrl());
        exercise.setVideoUrl(dto.videoUrl() == null ? exercise.getVideoUrl() : dto.videoUrl());
        return exercise;
    }
}
