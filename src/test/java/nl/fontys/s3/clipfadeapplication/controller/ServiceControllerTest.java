package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.domain.response.GetServicesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ServiceController.class)
@WithMockUser
class ServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetServicesUseCase getServicesUseCase;

    @MockBean
    private CreateServiceUseCase createServiceUseCase;

    @MockBean
    private GetServiceUseCase getServiceUseCase;

    @MockBean
    private UpdateServiceUseCase updateServiceUseCase;

    @MockBean
    private DeleteServiceUseCase deleteServiceUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @Test
    void getServices_shouldReturnResponseWithServicesArray() throws Exception {
        // Arrange
        GetServicesResponse response = GetServicesResponse.builder()
                .services(List.of(
                        Service.builder()
                                .id(2)
                                .name("test cut")
                                .description("test description")
                                .price(25.00)
                                .duration(30)
                                .appointments(Collections.emptyList())
                                .build(),
                        Service.builder()
                                .id(3)
                                .name("test full cut")
                                .description("test description")
                                .price(30.00)
                                .duration(45)
                                .appointments(Collections.emptyList())
                                .build()
                ))
                .build();

        when(getServicesUseCase.getServices()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/services")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.services").isArray());
    }

    @Test
    void getServices_shouldReturnResponseWithService() throws Exception {
        // Arrange
        int serviceId = 2;

        Service mockService = getService();
        when(getServiceUseCase.getService(serviceId)).thenReturn(Optional.of(mockService));

        // Act & Assert
        mockMvc.perform(get("/services/{id}", serviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(serviceId));
    }

    @Test
    void getServices_shouldReturnResponseWithServiceNotFound() throws Exception {
        // Arrange
        int serviceId = 2;

        when(getServiceUseCase.getService(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/services/{id}", serviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Service getService() {
        return Service.builder()
                .id(2)
                .name("test cut")
                .description("test description")
                .price(25.00)
                .duration(30)
                .appointments(Collections.emptyList())
                .build();
    }
}
