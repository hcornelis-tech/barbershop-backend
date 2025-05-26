package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUsersResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUsersUseCase getUsersUseCase;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockBean
    private GetUserUseCase getUserUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @Test
    void getUsers_shouldReturnResponseWithUsersArray() throws Exception {
        //Arrange
        GetUsersResponse response = GetUsersResponse.builder()
                .users(List.of(
                        GetUserResponse.builder()
                                .id(1)
                                .first_name("Jane")
                                .last_name("Doe")
                                .email("janedoe@gmail.com")
                                .phone_number("123")
                                .date_time("2024-11-04")
                                .is_barber(false)
                                .build(),
                        GetUserResponse.builder()
                                .id(1)
                                .first_name("Bob lee")
                                .last_name("Swagger")
                                .email("swagger@hotmail.com")
                                .phone_number("123")
                                .date_time("2024-11-04")
                                .is_barber(true)
                                .build()
                ))
                .build();

        when(getUsersUseCase.getUsers()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray());
    }

    @Test
    void getUser_shouldReturnResponseWithUser() throws Exception {
        //Arrange
        int userId = 1;

        GetUserResponse user = GetUserResponse.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .build();

        when(getUserUseCase.getUser(userId)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    void getUser_shouldReturnResponseWithUserNotFound() throws Exception {
        // Arrange
        int userId = 2;

        when(getUserUseCase.getUser(userId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
