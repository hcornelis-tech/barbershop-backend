package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetScheduleUseCaseImplTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @Test
    void getSchedule_ShouldReturnSchedule() {
        // Arrange
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(30)
                .build();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));

        GetScheduleUseCaseImpl getScheduleUseCase = new GetScheduleUseCaseImpl(scheduleRepository);

        // Act
        Optional<Schedule> response = getScheduleUseCase.getSchedule();

        // Assert
        assertEquals(schedule.getId(), response.get().getId());
        assertEquals(schedule.getStart_time(), response.get().getStart_time());
        assertEquals(schedule.getEnd_time(), response.get().getEnd_time());
    }
}
