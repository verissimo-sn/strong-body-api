package com.aitech.strongBody.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aitech.strongBody.entity.ExerciseDocument;
import com.aitech.strongBody.repository.ExerciseRepository;
import com.aitech.strongBody.useCase.exercise.CreateExerciseUseCase;
import com.aitech.strongBody.useCase.exercise.DeleteExerciseUseCase;
import com.aitech.strongBody.useCase.exercise.GetExerciseByIdUseCase;
import com.aitech.strongBody.useCase.exercise.GetExerciseListUseCase;
import com.aitech.strongBody.useCase.exercise.UpdateExerciseUseCase;

import java.util.List;

@Tag("Integration")
@DisplayName("ExerciseController")
@WebMvcTest(ExerciseController.class)
public class ExerciseControllerTests {
  @Autowired
  private MockMvc mockMvc;

  // @MockBean
  // private GetExerciseListUseCase getExercisesUseCase;
  // @MockBean
  // private CreateExerciseUseCase createExerciseUseCase;
  // @MockBean
  // private GetExerciseByIdUseCase getExerciseByIdUseCase;
  // @MockBean
  // private UpdateExerciseUseCase updateExerciseUseCase;
  // @MockBean
  // private DeleteExerciseUseCase deleteExerciseUseCase;

  @Mock
  private ExerciseRepository exerciseRepository;

  private ExerciseDocument fakeExerciseDocument;
  private List<ExerciseDocument> fakeExerciseDocumentList;

  @BeforeEach
  void setUp() {
    fakeExerciseDocument = new ExerciseDocument();
    fakeExerciseDocumentList = List.of(fakeExerciseDocument);
    // when(this.exerciseRepository.findAll()).thenReturn(fakeExerciseDocumentList);
    // when(this.exerciseRepository.findById()).thenReturn(exerciseRepository);
    // when(this.exerciseRepository.save()).thenReturn(exerciseRepository);
    // when(this.exerciseRepository.insert()).thenReturn(exerciseRepository);
    // when(this.exerciseRepository.deleteById()).thenReturn(exerciseRepository);
  }

  // @Test
  // @DisplayName("GET /exercises - Should return a list of exercises with status 200")
  // void shouldReturnAListOfExercises() throws Exception {
  //   when(this.exerciseRepository.findAll()).thenReturn(fakeExerciseDocumentList);
  //   // doReturn(fakeExerciseDocumentList).when(this.exerciseRepository).findAll();
  //   this.mockMvc.perform(get("/exercises"))
  //     .andExpect(status().isOk());
  // }

  @Test
  @DisplayName("GET /exercises/{:id} - Should return a exercise with status 200")
  void shouldReturnExercise() throws Exception {
    when(this.exerciseRepository.findById(this.fakeExerciseDocument.getId())).thenReturn(Optional.of(fakeExerciseDocument));
    // doReturn(fakeExerciseDocumentList).when(this.exerciseRepository).findAll();
    this.mockMvc.perform(get("/exercises/{:id}", this.fakeExerciseDocument.getId()))
      .andExpect(status().isOk());
  }
}
