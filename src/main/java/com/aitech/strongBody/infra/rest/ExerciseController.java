package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.exercise.*;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.infra.rest.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.infra.utils.PageableResponseMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exercises")
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
    public ResponseEntity<?> getExerciseList(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        var exercises = this.getExercisesUseCase.execute(pageable);
        var response = PageableResponseMapper.toPagination(exercises);
        logger.info("getExerciseList::ExerciseList: {}", response.get("totalItems"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundExercise = this.getExerciseByIdUseCase.execute(id);
        logger.info("getExerciseById::Id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(foundExercise);
    }

    @PostMapping
    public ResponseEntity<?> createExercise(@RequestBody @Valid CreateExerciseDto input) {
        Exercise exercise = new Exercise(
                input.name(),
                input.description(),
                input.level(),
                input.type(),
                input.equipment(),
                input.imageUrl(),
                input.videoUrl());
        this.createExerciseUseCase.execute(exercise);
        logger.info("createExercise::Exercise: {}", exercise.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(
            @RequestBody @Valid UpdateExerciseDto input,
            @PathVariable(value = "id") UUID id) {
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
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(
            @PathVariable(value = "id") UUID id) {
        this.deleteExerciseUseCase.execute(id);
        logger.info("deleteExercise::Id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
