package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.rest.dto.exercise.CreateExerciseDto;
import com.aitech.strongBody.infra.rest.dto.exercise.UpdateExerciseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("[Controller] Exercise Controller Test")
public class ExerciseControllerTest {
    private Exercise exercise;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExerciseRepository exerciseRepository;

    @BeforeEach
    void beforeEach() {
        this.exercise = Exercise.builder()
                .id(UUID.randomUUID())
                .name("Test Exercise")
                .description("Test Exercise Description")
                .level("Beginner")
                .type("Strength")
                .equipment("Dumbbell")
                .imageUrl(null)
                .videoUrl(null)
                .build();
        this.exerciseRepository.create(this.exercise);
    }

    @AfterEach
    void afterEach() {
        this.exerciseRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get exercise list with default pagination")
    void getExerciseList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").isNumber())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.currentPage").isNumber())
                .andExpect(jsonPath("$.data[0].id").isString())
                .andExpect(jsonPath("$.data[0].name").isString())
                .andExpect(jsonPath("$.data[0].description").isString())
                .andExpect(jsonPath("$.data[0].level").isString())
                .andExpect(jsonPath("$.data[0].type").isString())
                .andExpect(jsonPath("$.data[0].equipment").isString())
                .andExpect(jsonPath("$.data[0].imageUrl").isEmpty())
                .andExpect(jsonPath("$.data[0].videoUrl").isEmpty());
    }

    @Test
    @DisplayName("Should get exercise list with pagination")
    void getExerciseListWithPageParams() throws Exception {
        MultiValueMap<String, String> pageableParams = new LinkedMultiValueMap<>();
        pageableParams.add("page", "5");
        pageableParams.add("size", "10");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/exercises").params(pageableParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").isNumber())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.currentPage").value(5));
    }

    @Test
    @DisplayName("Should throw notFound exception when get exercise by id with not created exercise")
    void getExerciseByIdWithExerciseNotCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/exercises/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Exercise not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw badRequest exception when get exercise by id with invalid id")
    void getExerciseByIdWithInvalidUuid() throws Exception {
        // TODO: format error response message with a custom exception handler
        this.mockMvc.perform(MockMvcRequestBuilders.get("/exercises/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get exercise by id")
    void getExerciseById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/exercises/{id}", this.exercise.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.exercise.getId().toString()))
                .andExpect(jsonPath("$.name").value(this.exercise.getName()))
                .andExpect(jsonPath("$.description").value(this.exercise.getDescription()))
                .andExpect(jsonPath("$.level").value(this.exercise.getLevel()))
                .andExpect(jsonPath("$.type").value(this.exercise.getType()))
                .andExpect(jsonPath("$.equipment").value(this.exercise.getEquipment()))
                .andExpect(jsonPath("$.imageUrl").isEmpty())
                .andExpect(jsonPath("$.videoUrl").isEmpty());
    }

    @Test
    @DisplayName("Should create exercise")
    void createExercise() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/exercises")
                        .content(new ObjectMapper().writeValueAsString(new CreateExerciseDto(
                                this.exercise.getName(),
                                this.exercise.getDescription(),
                                this.exercise.getLevel(),
                                this.exercise.getType(),
                                this.exercise.getEquipment(),
                                this.exercise.getImageUrl(),
                                this.exercise.getVideoUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString());
    }

    @Test
    @DisplayName("Should update exercise with all fields")
    void updateExercise() throws Exception {
        var updatedExercise = Exercise.builder()
                .id(this.exercise.getId())
                .name("Updated Test Exercise")
                .description("Updated Test Exercise Description")
                .level("Intermediate")
                .type("Strength")
                .equipment("Dumbbell")
                .imageUrl("new image url")
                .videoUrl("new video url")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/exercises/{id}", this.exercise.getId())
                        .content(new ObjectMapper().writeValueAsString(new UpdateExerciseDto(
                                updatedExercise.getName(),
                                updatedExercise.getDescription(),
                                updatedExercise.getLevel(),
                                updatedExercise.getType(),
                                updatedExercise.getEquipment(),
                                updatedExercise.getImageUrl(),
                                updatedExercise.getVideoUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isOk());

        var foundExercise = this.exerciseRepository.getById(this.exercise.getId());
        foundExercise.ifPresent(exercise -> assertAll(
                () -> assertEquals(updatedExercise.getId(), exercise.getId()),
                () -> assertEquals(updatedExercise.getName(), exercise.getName()),
                () -> assertEquals(updatedExercise.getDescription(), exercise.getDescription()),
                () -> assertEquals(updatedExercise.getLevel(), exercise.getLevel()),
                () -> assertEquals(updatedExercise.getType(), exercise.getType()),
                () -> assertEquals(updatedExercise.getEquipment(), exercise.getEquipment()),
                () -> assertEquals(updatedExercise.getImageUrl(), exercise.getImageUrl()),
                () -> assertEquals(updatedExercise.getVideoUrl(), exercise.getVideoUrl())
        ));
    }

    @Test
    @DisplayName("Should throw badRequest when update exercise with invalid id")
    void throwUpdateExerciseWithInvalidId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/exercises/{id}", "invalid-id")
                        .content(new ObjectMapper().writeValueAsString(new UpdateExerciseDto(
                                this.exercise.getName(),
                                this.exercise.getDescription(),
                                this.exercise.getLevel(),
                                this.exercise.getType(),
                                this.exercise.getEquipment(),
                                this.exercise.getImageUrl(),
                                this.exercise.getVideoUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw notFound when update exercise with not created exercise")
    void throwUpdateExerciseWithNotCreatedExercise() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/exercises/{id}", UUID.randomUUID())
                        .content(new ObjectMapper().writeValueAsString(new UpdateExerciseDto(
                                this.exercise.getName(),
                                this.exercise.getDescription(),
                                this.exercise.getLevel(),
                                this.exercise.getType(),
                                this.exercise.getEquipment(),
                                this.exercise.getImageUrl(),
                                this.exercise.getVideoUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Exercise not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw notFound exception when delete exercise by id with not created exercise")
    void deleteExerciseByIdWithExerciseNotCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/exercises/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Exercise not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw badRequest exception when delete exercise by id with invalid id")
    void deleteExerciseByIdWithInvalidUuid() throws Exception {
        // TODO: format error response message with a custom exception handler
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/exercises/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should delete exercise by id")
    void deleteExerciseById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/exercises/{id}", this.exercise.getId()))
                .andExpect(status().isNoContent());

        var foundExercise = this.exerciseRepository.getById(this.exercise.getId());
        assertTrue(foundExercise.isEmpty());
    }
}
