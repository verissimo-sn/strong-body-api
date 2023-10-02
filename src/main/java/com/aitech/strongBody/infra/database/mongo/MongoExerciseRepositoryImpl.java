package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
@AllArgsConstructor
public class MongoExerciseRepositoryImpl implements ExerciseRepository {
    private static final Logger logger = LoggerFactory.getLogger(MongoExerciseRepositoryImpl.class);

    @Autowired
    private SpringDataMongoExerciseRepository repository;

    @Override
    public void create(Exercise exercise) {
        logger.info("create::Exercise: {}", exercise.toString());
        this.repository.save(this.fromEntityToDocument(exercise));
    }

    @Override
    public Optional<Exercise> getById(UUID id) {
        var foundExercise = this.repository.findById(id);
        logger.info("getById::Id: {}::Exercise: {}", id, foundExercise.toString());
        return foundExercise.map(this::fromDocumentToEntity);
    }

    @Override
    public Page<Exercise> getAll(Pageable pageable) {
        var exerciseList = this.repository.findAll(pageable).map(this::fromDocumentToEntity);
        logger.info("getAll::exerciseList: {}", exerciseList
                .getContent()
                .stream()
                .map(Exercise::getId)
                .toList());
        return exerciseList;
    }

    @Override
    public void update(Exercise exercise) {
        logger.info("update::Exercise: {}", exercise.toString());
        this.repository.save(this.fromEntityToDocument(exercise));
    }

    @Override
    public void deleteById(UUID id) {
        logger.info("deleteById::Id: {}", id);
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    private Exercise fromDocumentToEntity(ExerciseDocument document) {
        return Exercise
                .builder()
                .id(document.getId())
                .name(document.getName())
                .description(document.getDescription())
                .level(document.getLevel())
                .type(document.getType())
                .equipment(document.getEquipment())
                .imageUrl(document.getImageUrl())
                .videoUrl(document.getVideoUrl())
                .build();
    }

    private ExerciseDocument fromEntityToDocument(Exercise entity) {
        ExerciseDocument exerciseDocument = new ExerciseDocument();
        exerciseDocument.setId(entity.getId());
        exerciseDocument.setName(entity.getName());
        exerciseDocument.setDescription(entity.getDescription());
        exerciseDocument.setLevel(entity.getLevel());
        exerciseDocument.setType(entity.getType());
        exerciseDocument.setEquipment(entity.getEquipment());
        exerciseDocument.setImageUrl(entity.getImageUrl());
        exerciseDocument.setVideoUrl(entity.getVideoUrl());
        return exerciseDocument;
    }
}
