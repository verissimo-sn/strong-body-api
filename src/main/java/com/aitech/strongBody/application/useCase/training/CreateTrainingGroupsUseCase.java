package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class CreateTrainingGroupsUseCase {

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final ExerciseRepository exerciseRepository;

    public void execute(final UUID trainingId, final List<TrainingGroup> trainingGroups) {
        Training training = this.getTrainingById(trainingId);
        training.setTrainingGroups(trainingGroups.stream()
                .peek(this::reHydrateExerciseByIds)
                .toList());
        this.trainingRepository.update(training);
    }

    private void reHydrateExerciseByIds(TrainingGroup group) {
        var providedExercises = group.getExercises();
        List<UUID> exerciseIds = providedExercises
                    .stream()
                    .map(Exercise::getId)
                    .toList();
        var exercises = this.exerciseRepository.getByIds(exerciseIds);
        group.setExercises(exercises);
    }

    private Training getTrainingById(UUID id) {
        return this.trainingRepository.getById(id).orElseThrow(() -> {
            log.error("getTrainingById::Id: {}::Training not found", id);
            return new NotFoundException("Training not found");
        });
    }
}
