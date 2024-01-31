package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.exception.NotFoundException;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.domain.enums.UserRoles;
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
        this.user = new User(
                "Test user name",
                "test@mail.com",
                "test nickname",
                "http://test.com/avatar",
                "testPass",
                UserRoles.USER);
        this.userRepository.create(this.user);
    }

    @AfterEach
    void afterEach() {
        this.userRepository.deleteAll();
    }


    @Test
    @Disabled
    @DisplayName("Should throw notFound exception when get user by id when user not created")
    void throwExceptionWithGetUserByIdWhenUserNotCreated() throws Exception {
        // TODO: mock authentication user to enable this test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("User not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @Disabled
    @DisplayName("Should return logged user information")
    void getUserInformation() throws Exception {
        // TODO: mock authentication user to enable this test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.user.getId().toString()))
                .andExpect(jsonPath("$.name").value(this.user.getName()))
                .andExpect(jsonPath("$.email").value(this.user.getEmail()))
                .andExpect(jsonPath("$.nickname").value(this.user.getNickname()))
                .andExpect(jsonPath("$.avatarUrl").value(this.user.getAvatarUrl()));
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
                () -> assertEquals(updatedUser.getPassword(), user.getPassword())
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
