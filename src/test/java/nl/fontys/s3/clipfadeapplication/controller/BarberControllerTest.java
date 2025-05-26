package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarbersResponse;
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
@WebMvcTest(BarberController.class)
@WithMockUser
class BarberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetBarbersUseCase getBarbersUseCase;

    @MockBean
    private CreateBarberUseCase createBarberUseCase;

    @MockBean
    private UpdateBarberUseCase updateBarberUseCase;

    @MockBean
    private DeleteBarberUseCase deleteBarberUseCase;

    @MockBean
    private GetBarberUseCase getBarberUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @Test
    void getBarbers_shouldReturnResponseWithBarbersArray() throws Exception {
        //Arrange
        GetBarbersResponse response = GetBarbersResponse.builder()
                .barbers(List.of(
                        GetBarberResponse.builder()
                                .id(1)
                                .first_name("Boblee")
                                .last_name("Swagger")
                                .email("swagger@hotmail.com")
                                .phone_number("123")
                                .date_time("2024-11-04")
                                .hire_date("2024-11-04")
                                .is_admin(true)
                                .build(),
                        GetBarberResponse.builder()
                                .id(2)
                                .first_name("Boblee")
                                .last_name("Swagger")
                                .email("swagger@hotmail.com")
                                .phone_number("123")
                                .date_time("2024-11-04")
                                .hire_date("2024-11-04")
                                .is_admin(true)
                                .build()
                ))
                .build();

        when(getBarbersUseCase.getBarbers()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/barbers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barbers").isArray());
    }

    @Test
    void getBarber_shouldReturnResponseWithBarber() throws Exception {
        //Arrange
        int barberId = 1;

        GetBarberResponse barber = GetBarberResponse.builder()
                .id(1)
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .date_time("2024-11-04")
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();

        when(getBarberUseCase.getBarber(barberId)).thenReturn(barber);

        // Act & Assert
        mockMvc.perform(get("/barbers/{id}", barberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(barberId));
    }

    @Test
    void getBarber_shouldReturnResponseWithBarberNotFound() throws Exception {
        // Arrange
        int barberId = 1;

        when(getBarberUseCase.getBarber(barberId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/barbers/{id}", barberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
