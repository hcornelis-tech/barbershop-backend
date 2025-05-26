package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.CreateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.DeleteTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.GetTimeBlockersUseCase;
import nl.fontys.s3.clipfadeapplication.business.UpdateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockersResponse;
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
@WebMvcTest(TImeBlockerController.class)
@WithMockUser
class TimeBlockerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @MockBean
    private CreateTimeBlockerUseCase createTimeBlockerUseCase;

    @MockBean
    private GetTimeBlockersUseCase getTimeBlockersUseCase;

    @MockBean
    private DeleteTimeBlockerUseCase deleteTimeBlockerUseCase;

    @MockBean
    private UpdateTimeBlockerUseCase updateTimeBlockerUseCase;

    @Test
    void getTimeBlockers_shouldReturnResponseWithTimeBlockersArray() throws Exception {
        //Arrange
        int barberId = 1;

        GetTimeBlockersResponse response = GetTimeBlockersResponse.builder()
                .timeblockers(List.of(
                        GetTimeBlockerResponse.builder()
                                .id(1)
                                .start_date("2025-01-08")
                                .start_time("11:00")
                                .end_date("2025-01-09")
                                .end_time("11:00")
                                .all_day(false)
                                .build(),
                        GetTimeBlockerResponse.builder()
                                .id(2)
                                .start_date("2025-01-10")
                                .start_time("10:00")
                                .end_date("2025-01-10")
                                .end_time("12:00")
                                .all_day(false)
                                .build()
                ))
                .build();

        when(getTimeBlockersUseCase.getTimeBlockersByBarber(barberId)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/time-blocker/{id}", barberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeblockers").isArray());
    }
}
