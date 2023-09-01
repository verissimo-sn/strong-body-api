package com.aitech.strongBody.controller;

import com.aitech.strongBody.document.ExerciseDocument;
import com.aitech.strongBody.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.dto.exercise.UpdateExerciseDto;
import com.aitech.strongBody.useCase.exercise.*;
import com.aitech.strongBody.utils.PageableResponseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        var exercises = this.getExercisesUseCase.execute(pageable);
        var response = PageableResponseMapper.toPagination(exercises);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDocument> getExerciseById(
            @PathVariable(value="id") String id
    ) {
        var foundExercise = this.getExerciseByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundExercise);
    }

    @PostMapping
    public ResponseEntity<?> createExercise(@RequestBody @Valid CreateExerciseDto input) {
        this.createExerciseUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(
            @RequestBody @Valid UpdateExerciseDto input,
            @PathVariable(value="id") String id
    ) {
        this.updateExerciseUseCase.execute(input, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(
            @PathVariable(value="id") String id
    ) {
        this.deleteExerciseUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
