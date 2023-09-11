package com.aitech.strongBody.infra.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitech.strongBody.application.useCase.exercise.CreateExerciseUseCase;
import com.aitech.strongBody.application.useCase.exercise.DeleteExerciseUseCase;
import com.aitech.strongBody.application.useCase.exercise.GetExerciseByIdUseCase;
import com.aitech.strongBody.application.useCase.exercise.GetExerciseListUseCase;
import com.aitech.strongBody.application.useCase.exercise.UpdateExerciseUseCase;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.infra.rest.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.infra.utils.PageableResponseMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(
            @PathVariable(value = "id") @Valid UUID id) {
        var foundExercise = this.getExerciseByIdUseCase.execute(id);
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
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(
            @PathVariable(value = "id") UUID id) {
        this.deleteExerciseUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
