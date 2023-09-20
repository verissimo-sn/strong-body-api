package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoExerciseRepositoryImpl implements ExerciseRepository {
    private final SpringDataMongoExerciseRepository repository;

    public MongoExerciseRepositoryImpl(SpringDataMongoExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Exercise exercise) {
        this.repository.save(this.fromEntityToDocument(exercise));
    }

    @Override
    public Optional<Exercise> getById(UUID id) {
        var foundExercise = this.repository.findById(id);
        return foundExercise.map(this::fromDocumentToEntity);
    }

    @Override
    public Page<Exercise> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this::fromDocumentToEntity);
    }

    @Override
    public void update(Exercise exercise) {
        this.repository.save(this.fromEntityToDocument(exercise));
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
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
