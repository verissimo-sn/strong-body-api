package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.infra.database.h2.model.TrainingH2;
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
public class H2TrainingRepositoryImpl implements TrainingRepository {
    private static final Logger logger = LoggerFactory.getLogger(H2TrainingRepositoryImpl.class);

    @Autowired
    private SpringDataH2TrainingRepository repository;

    @Override
    public void create(Training training) {
        logger.info("create::Training: {}", training.toString());
        this.repository.save(this.toModel(training));
    }

    @Override
    public Optional<Training> getById(UUID id) {
        var foundTraining = this.repository.findById(id);
        logger.info("getById::Id: {}::Training: {}", id, foundTraining.toString());
        return foundTraining.map(this::toEntity);
    }

    @Override
    public Page<Training> filterByName(String name, Pageable pageable) {
        var trainingList = this.repository.findByNameLikeOrderByNameAsc(name, pageable).map(this::toEntity);
        logger.info("filterByName::trainingList: {}", trainingList
                .getContent()
                .stream()
                .map(Training::getName)
                .toList());
        return trainingList;
    }

    @Override
    public Page<Training> getAll(Pageable pageable) {
        var trainingList = this.repository.findAll(pageable).map(this::toEntity);
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
        this.repository.save(this.toModel(training));
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

    private Training toEntity(TrainingH2 model) {
        return Training
                .builder()
                .id(model.getId())
                .userId(model.getUserId())
                .name(model.getName())
                .level(model.getLevel())
                .status(model.getStatus())
                .requiredSessions(model.getRequiredSessions())
                .finishedSessions(model.getFinishedSessions())
                .build();
    }

    private TrainingH2 toModel(Training entity) {
        TrainingH2 trainingModel = new TrainingH2();
        trainingModel.setId(entity.getId());
        trainingModel.setUserId(entity.getUserId());
        trainingModel.setName(entity.getName());
        trainingModel.setLevel(entity.getLevel());
        trainingModel.setLevel(entity.getLevel());
        trainingModel.setStatus(entity.getStatus());
        trainingModel.setRequiredSessions(entity.getRequiredSessions());
        trainingModel.setFinishedSessions(entity.getFinishedSessions());
        return trainingModel;
    }
}
