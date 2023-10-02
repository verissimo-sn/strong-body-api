package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.training.*;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.infra.rest.dto.training.CreateTrainingDto;
import com.aitech.strongBody.infra.rest.dto.training.CreateTrainingGroupsDto;
import com.aitech.strongBody.infra.rest.dto.training.UpdateTrainingDto;
import com.aitech.strongBody.infra.utils.PageableResponseMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/trainings")
@Tag(name = "Training", description = "Training API")
public class TrainingController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @Autowired
    private GetTrainingListUseCase getTrainingListUseCase;
    @Autowired
    private CreateTrainingUseCase createTrainingUseCase;
    @Autowired
    private FilterTrainingListByNameUseCase trainingListByNameUseCase;
    @Autowired
    private UpdateTrainingUseCase updateTrainingUseCase;
    @Autowired
    private GetTrainingByIdUseCase getTrainingByIdUseCase;
    @Autowired
    private DeleteTrainingUseCase deleteTrainingUseCase;
    @Autowired
    private CreateTrainingGroupsUseCase createTrainingGroupsUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getTrainingList(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        var trainings = this.getTrainingListUseCase.execute(pageable);
        var response = PageableResponseMapper.toPagination(trainings);
        logger.info("getTrainingList::TrainingList: {}", response.get("totalItems"));
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Training getTrainingById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundTraining = this.getTrainingByIdUseCase.execute(id);
        logger.info("getTrainingById::Id: {}", id);
        return foundTraining;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> filterTrainingByName(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam("name") @Valid @NotBlank String name) {
        var foundTraining = this.trainingListByNameUseCase.execute(name, pageable);
        logger.info("filterTrainingByName::Name: {}", name);
        return PageableResponseMapper.toPagination(foundTraining);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTraining(@RequestBody @Valid CreateTrainingDto input) {
        Training training = new Training(
                input.userId(),
                input.name(),
                input.level());
        this.createTrainingUseCase.execute(training);
        logger.info("createTraining::Training: {}", training.toString());
    }

    @PostMapping("/{id}/training-groups")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrainingGroups(
            @PathVariable(value = "id") @Valid UUID id,
            @RequestBody @Valid CreateTrainingGroupsDto[] input) {
        List<TrainingGroup> trainingGroups = Arrays.stream(input)
                .map(trainingGroup -> {
                    var exerciseIds = trainingGroup.exercisesId()
                                        .stream()
                                        .map(UUID::fromString)
                                        .toArray(UUID[]::new);
                    var exercises = Arrays.stream(exerciseIds)
                            .map(exerciseId -> {
                                Exercise exercise = new Exercise();
                                exercise.setId(exerciseId);
                                return exercise;
                            }).toList();
                    var group = new TrainingGroup();
                    group.setTag(trainingGroup.tag());
                    group.setDescription(trainingGroup.description());
                    group.setOrder(trainingGroup.order());
                    group.setExercises(exercises);
                    return group;
                }).toList();
        this.createTrainingGroupsUseCase.execute(id, trainingGroups);
//        logger.info("createTrainingGroups::TrainingGroups: {}", training.toString());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTraining(
            @RequestBody @Valid UpdateTrainingDto input,
            @PathVariable(value = "id") @Valid @NotBlank UUID id) {
        Training training = Training.builder()
                .id(id)
                .userId(input.userId())
                .name(input.name())
                .level(input.level())
                .build();
        this.updateTrainingUseCase.execute(training);
        logger.info("updateTraining::Exercise{}", training.toString());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(
            @PathVariable(value = "id") @Valid UUID id) {
        this.deleteTrainingUseCase.execute(id);
        logger.info("deleteTraining::Id: {}", id);
    }
}
