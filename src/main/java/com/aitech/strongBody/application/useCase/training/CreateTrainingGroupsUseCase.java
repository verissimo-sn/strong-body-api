package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CreateTrainingGroupsUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateTrainingGroupsUseCase.class);

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final ExerciseRepository exerciseRepository;

    public void execute(UUID trainingId, List<TrainingGroup> trainingGroups) {
        Training training = this.getTrainingById(trainingId);
        var trainingGroup = trainingGroups.stream().map(group -> {
            var providedExercises = group.getExercises();
            UUID[] exerciseIds = providedExercises
                        .stream()
                        .map(Exercise::getId)
                        .toArray(UUID[]::new);
            List<Exercise> foundedExercises = this.getExercisesByIds(exerciseIds);
            var validatedExercises = this.validateProvidedAndFoundedExercises(providedExercises, foundedExercises);
            validatedExercises.forEach(group::addExercise);
            return group;
        }).toList();
        training.setTrainingGroups(trainingGroup);
        this.trainingRepository.update(training);
    }

    private Training getTrainingById(UUID id) {
        var foundTraining = this.trainingRepository.getById(id);
        if(foundTraining.isEmpty()) {
            logger.error("getTrainingById::Id: {}::Training not found", id);
            throw new NotFoundException("Training not found");
        }
        return foundTraining.get();
    }

    private List<Exercise> getExercisesByIds(UUID[] ids) {
        var foundExercises = this.exerciseRepository.getByIds(ids);
        if(foundExercises.isEmpty()) {
            logger.error("getExercisesByIds::Ids: {}", Arrays.stream(ids).toList().toString());
        }
        return foundExercises;
    }

    private List<Exercise> validateProvidedAndFoundedExercises(
            List<Exercise> providedExercises,
            List<Exercise> foundedExercises) {
        List<Exercise> validExercises = new ArrayList<>();
        List<Exercise> invalidExercises = new ArrayList<>();
        for (Exercise provided: providedExercises) {
            Optional<Exercise> exerciseMatched = foundedExercises.stream()
                        .filter(exercise -> exercise.getId() == provided.getId())
                        .findFirst();
            exerciseMatched.ifPresent(exercise -> {
                if(exercise.equals(provided)) {
                    validExercises.add(provided);
                } else {
                    invalidExercises.add(provided);
                }
            });
        }
        logger.warn("validateProvidedAndFoundedExercises::Invalid exercises: {}",
                    invalidExercises.stream().map(Exercise::getId).toList().toString());
        return validExercises;
    }
}
