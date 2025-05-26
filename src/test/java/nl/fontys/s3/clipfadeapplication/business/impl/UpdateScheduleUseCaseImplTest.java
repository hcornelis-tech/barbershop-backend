package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidSlotIntervalException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateScheduleRequest;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateScheduleUseCaseImplTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private UpdateScheduleUseCaseImpl updateScheduleUseCase;

    @Test
    void updateSchedule_shouldUpdateSchedule() {
        //Arrange
        UpdateScheduleRequest request = getUpdateScheduleRequest();
        ScheduleEntity schedule = getSchedule();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));

        //Act
        updateScheduleUseCase.updateSchedule(request);

        //Assert
        assertEquals(request.getService_duration(), schedule.getService_duration());
    }

    @Test
    void updateSchedule_shouldThrowDateTimeInThePastException_whenTimeEndTimeIsBeforeStartTime() {
        // Arrange
        UpdateScheduleRequest request = getUpdateScheduleRequest();
        request.setEnd_time("09:00");

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> updateScheduleUseCase.updateSchedule(request));
    }

    @Test
    void updateSchedule_shouldThrowRuntimeException_whenScheduleDontExists() {
        // Arrange
        UpdateScheduleRequest request = getUpdateScheduleRequest();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> updateScheduleUseCase.updateSchedule(request));
    }

    @Test
    void updateSchedule_shouldInvalidSlotIntervalException_whenInvalidSlotInterval() {
        // Arrange
        UpdateScheduleRequest request = getUpdateScheduleRequest();
        request.setService_duration(5);
        ScheduleEntity schedule = getSchedule();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));

        // Act & Assert
        assertThrows(InvalidSlotIntervalException.class, () -> updateScheduleUseCase.updateSchedule(request));
    }

    private ScheduleEntity getSchedule() {
        return ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(15)
                .build();
    }

    private UpdateScheduleRequest getUpdateScheduleRequest() {
        return UpdateScheduleRequest.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(30)
                .build();
    }
}
