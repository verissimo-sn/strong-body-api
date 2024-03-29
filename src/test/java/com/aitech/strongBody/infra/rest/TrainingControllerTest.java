package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.entity.TrainingGroup;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.enums.TrainingStatus;
import com.aitech.strongBody.domain.enums.UserRoles;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.rest.dto.training.CreateTrainingDto;
import com.aitech.strongBody.infra.rest.dto.training.CreateTrainingGroupsDto;
import com.aitech.strongBody.infra.rest.dto.training.UpdateTrainingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("[Controller] TrainingControllerTest")
public class TrainingControllerTest {
    private Training training;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @BeforeEach
    void beforeEach() {
        var exercises = List.of(new Exercise());
        TrainingGroup trainingGroup = TrainingGroup.builder().tag("A").description("description").order(1).exercises(exercises).build();
        var user = new User(
                "Test user name",
                "test@mail.com",
                "test nickname",
                "http://test.com/avatar",
                "testPass",
                UserRoles.USER);
        this.training = Training.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .status(TrainingStatus.ACTIVE)
                .name("Test training")
                .level("Beginner")
                .trainingGroups(List.of(trainingGroup))
                .build();
        this.trainingRepository.create(this.training);
        this.userRepository.create(user);
        this.exerciseRepository.create(exercises.get(0));
    }

    @AfterEach
    void afterEach() {
        this.trainingRepository.deleteAll();
        this.userRepository.deleteAll();
        this.exerciseRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get training list with default pagination")
    void getTrainingList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").isNumber())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.currentPage").isNumber())
                .andExpect(jsonPath("$.data[0].id").isString())
                .andExpect(jsonPath("$.data[0].userId").isString())
                .andExpect(jsonPath("$.data[0].name").isString())
                .andExpect(jsonPath("$.data[0].level").isString())
                .andExpect(jsonPath("$.data[0].status").isString())
                .andExpect(jsonPath("$.data[0].requiredSessions").isNumber())
                .andExpect(jsonPath("$.data[0].finishedSessions").isNumber());
    }

    @Test
    @DisplayName("Should get training list with pagination")
    void getTrainingListWithPageParams() throws Exception {
        MultiValueMap<String, String> pageableParams = new LinkedMultiValueMap<>();
        pageableParams.add("page", "5");
        pageableParams.add("size", "10");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings").params(pageableParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.currentPage").value(5));
    }

    @Test
    @DisplayName("Should throw notFound exception when get training by id with not created training")
    void getTrainingByIdWithTrainingNotCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Training not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw badRequest exception when get training by id with invalid id")
    void getTrainingByIdWithInvalidId() throws Exception {
        // TODO: format error response message with a custom exception handler
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get trainings by id")
    void getTrainingById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings/{id}", this.training.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.training.getId().toString()))
                .andExpect(jsonPath("$.userId").value(this.training.getUserId().toString()))
                .andExpect(jsonPath("$.name").value(this.training.getName()))
                .andExpect(jsonPath("$.level").value(this.training.getLevel()))
                .andExpect(jsonPath("$.status").value(this.training.getStatus().toString()))
                .andExpect(jsonPath("$.requiredSessions").value(this.training.getRequiredSessions()))
                .andExpect(jsonPath("$.finishedSessions").value(this.training.getFinishedSessions()));
    }

    @Test
    @DisplayName("Should filter trainings by name")
    void getTrainingsByName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings/search")
                        .param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").isNumber())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.currentPage").isNumber())
                .andExpect(jsonPath("$.data[0].id").value(this.training.getId().toString()))
                .andExpect(jsonPath("$.data[0].userId").value(this.training.getUserId().toString()))
                .andExpect(jsonPath("$.data[0].name").value(this.training.getName()))
                .andExpect(jsonPath("$.data[0].level").value(this.training.getLevel()))
                .andExpect(jsonPath("$.data[0].status").value(this.training.getStatus().toString()))
                .andExpect(jsonPath("$.data[0].requiredSessions").value(0))
                .andExpect(jsonPath("$.data[0].finishedSessions").value(0));
    }

    @Test
    @DisplayName("Should throw badRequest with filter trainings by name when name not provided")
    void throwExceptionWhenNameNotProvided() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trainings/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled
    @DisplayName("Should create trainings")
    void createTraining() throws Exception {
        // TODO: mock authentication user to enable this test
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/trainings")
                        .content(new ObjectMapper().writeValueAsString(new CreateTrainingDto(
                                this.training.getName(),
                                this.training.getLevel()
                        )))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString());
    }

    @Test
    @DisplayName("Should create training groups correct")
    void createTrainingGroups() throws Exception {
        CreateTrainingGroupsDto[] input = new CreateTrainingGroupsDto[]{
                new CreateTrainingGroupsDto(
                        this.training.getTrainingGroups().get(0).getTag(),
                        this.training.getTrainingGroups().get(0).getDescription(),
                        this.training.getTrainingGroups().get(0).getOrder(),
                        List.of(this.training.getTrainingGroups().get(0).getExercises().get(0).getId().toString())
                )
        };
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/trainings/{id}/training-groups", this.training.getId())
                        .content(new ObjectMapper().writeValueAsString(input))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should throw badRequest when update training with invalid id")
    void throwUpdateTrainingWithInvalidId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/trainings/{id}", "invalid-id")
                        .content(new ObjectMapper().writeValueAsString(new UpdateTrainingDto(
                                this.training.getName(),
                                this.training.getLevel()
                        )))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled
    @DisplayName("Should throw notFound when update training with not created training")
    void throwUpdateTrainingWithNotCreatedTraining() throws Exception {
        // TODO: mock authentication user to enable this test
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/trainings/{id}", UUID.randomUUID())
                        .content(new ObjectMapper().writeValueAsString(new UpdateTrainingDto(
                                this.training.getName(),
                                this.training.getLevel()
                        )))
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Training not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw notFound exception when delete training by id with not created training")
    void deleteTrainingByIdWithTrainingNotCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/trainings/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Training not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw badRequest exception when delete training by id with invalid id")
    void deleteTrainingByIdWithInvalidId() throws Exception {
        // TODO: format error response message with a custom exception handler
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/trainings/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should delete training by id")
    void deleteTrainingById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/trainings/{id}", this.training.getId()))
                .andExpect(status().isNoContent());

        var foundTraining = this.trainingRepository.getById(this.training.getId());
        assertTrue(foundTraining.isEmpty());
    }
}
