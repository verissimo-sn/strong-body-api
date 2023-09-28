package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.repository.UserRepository;
import com.aitech.strongBody.infra.rest.dto.user.CreateUserDto;
import com.aitech.strongBody.infra.rest.dto.user.UpdateUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("[Controller] User Controller Test")
public class UserControllerTest {
    private User user;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        this.user = User.builder()
                .id(UUID.randomUUID())
                .name("Test user name")
                .email("test@mail.com")
                .nickname("test nickname")
                .avatarUrl("http://test.com/avatar")
                .password("testPass")
                .build();
        this.userRepository.create(this.user);
    }

    @AfterEach
    void afterEach() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("Should throw notFound exception when get user by id when user not created")
    void throwExceptionWithGetUserByIdWhenUserNotCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("User not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Should throw badRequest exception when get user by id with invalid id")
    void ThrowExceptionWhenGetUserByIdWithInvalidId() throws Exception {
        // TODO: format error response message with a custom exception handler
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get user by id")
    void getUserById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", this.user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.user.getId().toString()))
                .andExpect(jsonPath("$.name").value(this.user.getName()))
                .andExpect(jsonPath("$.email").value(this.user.getEmail()))
                .andExpect(jsonPath("$.nickname").value(this.user.getNickname()))
                .andExpect(jsonPath("$.avatarUrl").value(this.user.getAvatarUrl()))
                .andExpect(jsonPath("$.password").isEmpty());
    }

    @Test
    @DisplayName("Should create user")
    void createUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(new ObjectMapper().writeValueAsString(new CreateUserDto(
                                this.user.getName(),
                                "valid@mail.com.br",
                                this.user.getNickname(),
                                this.user.getAvatarUrl(),
                                this.user.getPassword()
                        )))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should update user with correct values")
    void updateUser() throws Exception {
        var updatedUser = User.builder()
                .id(this.user.getId())
                .name("Updated Test User Name")
                .email(this.user.getEmail())
                .nickname("update_nickname")
                .password(this.user.getPassword())
                .avatarUrl("http://test.com/avatar-updated")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", this.user.getId())
                        .content(new ObjectMapper().writeValueAsString(new UpdateUserDto(
                                updatedUser.getName(),
                                updatedUser.getNickname(),
                                updatedUser.getAvatarUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isOk());

        var foundUser = this.userRepository.getById(this.user.getId());
        foundUser.ifPresent(user -> assertAll(
                () -> assertEquals(updatedUser.getId(), user.getId()),
                () -> assertEquals(updatedUser.getName(), user.getName()),
                () -> assertEquals(updatedUser.getEmail(), user.getEmail()),
                () -> assertEquals(updatedUser.getNickname(), user.getNickname()),
                () -> assertEquals(updatedUser.getAvatarUrl(), user.getAvatarUrl()),
                () -> assertNull(user.getPassword())
        ));
    }

    @Test
    @DisplayName("Should throw badRequest when update user with invalid id")
    void throwUpdateUserWithInvalidId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", "invalid-id")
                        .content(new ObjectMapper().writeValueAsString(new CreateUserDto(
                                this.user.getName(),
                                "valid@mail.com.br",
                                this.user.getNickname(),
                                this.user.getAvatarUrl(),
                                this.user.getPassword()
                        )))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw notFound with update user when user not found")
    void throwExceptionWithUpdateUserByIdWhenUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", UUID.randomUUID())
                        .content(new ObjectMapper().writeValueAsString(new UpdateUserDto(
                                this.user.getName(),
                                this.user.getNickname(),
                                this.user.getAvatarUrl()
                        )))
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("User not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
