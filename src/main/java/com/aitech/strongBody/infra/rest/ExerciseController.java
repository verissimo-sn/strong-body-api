package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.exercise.*;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.infra.rest.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.infra.rest.dto.shared.IdentifierDto;
import com.aitech.strongBody.infra.utils.PageableResponseMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@Tag(name = "Exercise", description = "Exercise API")
public class ExerciseController {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    @Autowired
    GetExerciseListUseCase getExercisesUseCase;
    @Autowired
    CreateExerciseUseCase createExerciseUseCase;
    @Autowired
    GetExerciseByIdUseCase getExerciseByIdUseCase;
    @Autowired
    UpdateExerciseUseCase updateExerciseUseCase;
    @Autowired
    DeleteExerciseUseCase deleteExerciseUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getExerciseList(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        var exercises = this.getExercisesUseCase.execute(pageable);
        var response = PageableResponseMapper.toPagination(exercises);
        logger.info("getExerciseList::ExerciseList: {}", response.get("totalItems"));
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Exercise getExerciseById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundExercise = this.getExerciseByIdUseCase.execute(id);
        logger.info("getExerciseById::Id: {}", id);
        return foundExercise;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdentifierDto createExercise(@RequestBody @Valid CreateExerciseDto input) {
        Exercise exercise = new Exercise(
                input.name(),
                input.description(),
                input.level(),
                input.type(),
                input.equipment(),
                input.imageUrl(),
                input.videoUrl());
        UUID id = this.createExerciseUseCase.execute(exercise);
        logger.info("createExercise::Exercise: {}", exercise.toString());
        return new IdentifierDto(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateExercise(
            @RequestBody @Valid UpdateExerciseDto input,
            @PathVariable(value = "id") @Valid UUID id) {
        Exercise exercise = Exercise.builder()
                .id(id)
                .name(input.name())
                .description(input.description())
                .level(input.level())
                .type(input.type())
                .equipment(input.equipment())
                .imageUrl(input.imageUrl())
                .videoUrl(input.videoUrl())
                .build();
        this.updateExerciseUseCase.execute(exercise);
        logger.info("updateExercise::Exercise{}", exercise.toString());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(
            @PathVariable(value = "id") UUID id) {
        this.deleteExerciseUseCase.execute(id);
        logger.info("deleteExercise::Id: {}", id);
    }
}
