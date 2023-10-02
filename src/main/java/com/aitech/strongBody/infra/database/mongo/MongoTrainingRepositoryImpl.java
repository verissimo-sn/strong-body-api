package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import com.aitech.strongBody.infra.database.mongo.model.TrainingDocument;
import com.aitech.strongBody.infra.database.mongo.model.TrainingGroupDocument;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
@AllArgsConstructor
public class MongoTrainingRepositoryImpl implements TrainingRepository {
    private static final Logger logger = LoggerFactory.getLogger(MongoTrainingRepositoryImpl.class);

    @Autowired
    private final SpringDataMongoTrainingRepository trainingRepository;

    @Override
    public void create(Training training) {
        logger.info("create::Training: {}", training.toString());
        this.trainingRepository.save(this.fromEntityToDocument(training));
    }

    @Override
    public Optional<Training> getById(UUID id) {
        var foundTraining = this.trainingRepository.findById(id);
        logger.info("getById::Id: {}::Training: {}", id, foundTraining.toString());
        return foundTraining.map(this::fromDocumentToEntity);
    }

    @Override
    public Page<Training> filterByName(String name, Pageable pageable) {
        var trainingList = this.trainingRepository.findByNameLikeOrderByNameAsc(name, pageable).map(this::fromDocumentToEntity);
        logger.info("filterByName::trainingList: {}", trainingList
                .getContent()
                .stream()
                .map(Training::getName)
                .toList());
        return trainingList;
    }

    @Override
    public Page<Training> getAll(Pageable pageable) {
        var trainingList = this.trainingRepository.findAll(pageable).map(this::fromDocumentToEntity);
        logger.info("getAll::trainingList: {}", trainingList
                .getContent()
                .stream()
                .map(Training::getId)
                .toList());
        return trainingList;
    }

    @Override
    public void update(Training training) {
        logger.info("update::Training: {}", training.toString());
        this.trainingRepository.save(this.fromEntityToDocument(training));
    }

    @Override
    public void deleteById(UUID id) {
        logger.info("deleteById::Id: {}", id);
        this.trainingRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.trainingRepository.deleteAll();
    }

    private Training fromDocumentToEntity(TrainingDocument document) {
        var training = new Training();
        training.setId(document.getId());
        training.setUserId(document.getUserId());
        training.setName(document.getName());
        training.setLevel(document.getLevel());
        training.setStatus(document.getStatus());
        training.setRequiredSessions(document.getRequiredSessions());
        training.setFinishedSessions(document.getFinishedSessions());
        List<TrainingGroup> trainingGroups = document.getTrainingGroups().stream().map(groups -> {
            var exercises = groups.getExercises().stream().map(exercise -> {
                var exerciseEntity = new Exercise();
                exerciseEntity.setId(exercise.getId());
                exerciseEntity.setName(exercise.getName());
                exerciseEntity.setDescription(exercise.getDescription());
                exerciseEntity.setLevel(exercise.getLevel());
                exerciseEntity.setType(exercise.getType());
                exerciseEntity.setEquipment(exercise.getEquipment());
                exerciseEntity.setImageUrl(exercise.getImageUrl());
                exerciseEntity.setVideoUrl(exercise.getVideoUrl());
                return exerciseEntity;
            }).toList();
            var trainingGroup = new TrainingGroup();
            trainingGroup.setTag(groups.getTag());
            trainingGroup.setDescription(groups.getDescription());
            trainingGroup.setOrder(groups.getOrder());
            trainingGroup.setExercises(exercises);
            return  trainingGroup;
        }).toList();
        training.setTrainingGroups(trainingGroups);
        return training;
    }

    private TrainingDocument fromEntityToDocument(Training entity) {
        logger.warn("fromEntityToDocument::Training: {}", entity.getTrainingGroups().toString());
        TrainingDocument trainingDocument = new TrainingDocument();
        trainingDocument.setId(entity.getId());
        trainingDocument.setUserId(entity.getUserId());
        trainingDocument.setName(entity.getName());
        trainingDocument.setLevel(entity.getLevel());
        trainingDocument.setStatus(entity.getStatus());
        trainingDocument.setRequiredSessions(entity.getRequiredSessions());
        trainingDocument.setFinishedSessions(entity.getFinishedSessions());
        List<TrainingGroupDocument> trainingGroups = entity.getTrainingGroups().stream().map(groups -> {
            var exercises = groups.getExercises().stream().map(exercise -> {
                var exerciseDocument = new ExerciseDocument();
                exerciseDocument.setId(exercise.getId());
                exerciseDocument.setName(exercise.getName());
                exerciseDocument.setDescription(exercise.getDescription());
                exerciseDocument.setLevel(exercise.getLevel());
                exerciseDocument.setType(exercise.getType());
                exerciseDocument.setEquipment(exercise.getEquipment());
                exerciseDocument.setImageUrl(exercise.getImageUrl());
                exerciseDocument.setVideoUrl(exercise.getVideoUrl());
                return exerciseDocument;
            }).toList();
            var trainingGroup = new TrainingGroupDocument();
            trainingGroup.setTag(groups.getTag());
            trainingGroup.setDescription(groups.getDescription());
            trainingGroup.setOrder(groups.getOrder());
            trainingGroup.setExercises(exercises);
            return  trainingGroup;
        }).toList();
        trainingDocument.setTrainingGroups(trainingGroups);
        return trainingDocument;
    }
}
