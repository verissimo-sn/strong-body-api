package com.aitech.strongBody.infra.database.mongo;

import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.infra.database.mongo.model.TrainingDocument;
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
public class MongoTrainingRepositoryImpl implements TrainingRepository {
    private static final Logger logger = LoggerFactory.getLogger(MongoTrainingRepositoryImpl.class);

    @Autowired
    private SpringDataMongoTrainingRepository repository;

    @Override
    public void create(Training training) {
        logger.info("create::Training: {}", training.toString());
        this.repository.save(this.fromEntityToDocument(training));
    }

    @Override
    public Optional<Training> getById(UUID id) {
        var foundTraining = this.repository.findById(id);
        logger.info("getById::Id: {}::Training: {}", id, foundTraining.toString());
        return foundTraining.map(this::fromDocumentToEntity);
    }

    @Override
    public Page<Training> filterByName(String name, Pageable pageable) {
        var trainingList = this.repository.findByNameLikeOrderByNameAsc(name, pageable).map(this::fromDocumentToEntity);
        logger.info("filterByName::trainingList: {}", trainingList
                .getContent()
                .stream()
                .map(Training::getName)
                .toList());
        return trainingList;
    }

    @Override
    public Page<Training> getAll(Pageable pageable) {
        var trainingList = this.repository.findAll(pageable).map(this::fromDocumentToEntity);
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
        this.repository.save(this.fromEntityToDocument(training));
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

    private Training fromDocumentToEntity(TrainingDocument document) {
        return Training
                .builder()
                .id(document.getId())
                .userId(document.getUserId())
                .name(document.getName())
                .level(document.getLevel())
                .status(document.getStatus())
                .requiredSessions(document.getRequiredSessions())
                .finishedSessions(document.getFinishedSessions())
                .build();
    }

    private TrainingDocument fromEntityToDocument(Training entity) {
        TrainingDocument trainingDocument = new TrainingDocument();
        trainingDocument.setId(entity.getId());
        trainingDocument.setUserId(entity.getUserId());
        trainingDocument.setName(entity.getName());
        trainingDocument.setLevel(entity.getLevel());
        trainingDocument.setLevel(entity.getLevel());
        trainingDocument.setStatus(entity.getStatus());
        trainingDocument.setRequiredSessions(entity.getRequiredSessions());
        trainingDocument.setFinishedSessions(entity.getFinishedSessions());
        return trainingDocument;
    }
}
