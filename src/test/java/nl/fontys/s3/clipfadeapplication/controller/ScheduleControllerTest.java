package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.GetScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.business.UpdateScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ScheduleController.class)
@WithMockUser
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetScheduleUseCase getScheduleUseCase;

    @MockBean
    private UpdateScheduleUseCase updateScheduleUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @Test
    void getSchedule_shouldReturnResponseWithSchedule() throws Exception {
        //Arrange
        Schedule schedule = Schedule.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(30)
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .friday(true)
                .saturday(true)
                .sunday(false)
                .build();

        when(getScheduleUseCase.getSchedule()).thenReturn(Optional.of(schedule));

        // Act & Assert
        mockMvc.perform(get("/schedule")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schedule.getId()));
    }
}
