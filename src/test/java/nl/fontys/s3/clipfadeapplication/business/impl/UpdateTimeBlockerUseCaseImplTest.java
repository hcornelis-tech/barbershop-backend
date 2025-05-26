package nl.fontys.s3.clipfadeapplication.business.impl;


import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidTimeBlockerException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
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
class UpdateTimeBlockerUseCaseImplTest {
    @Mock
    private TimeBlockerRepository timeBlockerRepository;

    @InjectMocks
    private UpdateTimeBlockerUseCaseImpl updateTimeBlockerUseCase;

    @Test
    void updateTimeBlocker_shouldUpdateTimeBlocker() {
        //Arrange
        UpdateTimeBlockerRequest request = getTimeBlockerRequest();
        BarberEntity barber = getBarber();
        TimeBlockerEntity timeBlocker = getTimeBlocker(barber);

        when(timeBlockerRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(timeBlocker));

        //Act
        updateTimeBlockerUseCase.updateTimeBlocker(request);

        //Assert
        assertEquals(request.getStart_time(), timeBlocker.getStart_time());
    }

    @Test
    void updateTimeBlocker_shouldThrowInvalidTimeBlockerException_whenTimeBlockerDoesNotExist() {
        // Arrange
        UpdateTimeBlockerRequest request = getTimeBlockerRequest();
        when(timeBlockerRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidTimeBlockerException.class, () -> updateTimeBlockerUseCase.updateTimeBlocker(request));
    }

    @Test
    void updateTimeBlocker_shouldThrowDateTimeInThePastException_whenTimeEndDateIsBeforeStartDate() {
        // Arrange
        UpdateTimeBlockerRequest request = getTimeBlockerRequest();
        request.setEnd_date("2024-11-01");

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> updateTimeBlockerUseCase.updateTimeBlocker(request));
    }

    @Test
    void updateTimeBlocker_shouldThrowDateTimeInThePastException_whenTimeEndTimeIsBeforeStartTime() {
        // Arrange
        UpdateTimeBlockerRequest request = getTimeBlockerRequest();
        request.setEnd_time("09:00");

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> updateTimeBlockerUseCase.updateTimeBlocker(request));
    }

    private TimeBlockerEntity getTimeBlocker(BarberEntity barber) {
        return TimeBlockerEntity.builder()
                .barber(barber)
                .start_date("2024-11-04")
                .end_date("2024-11-06")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();
    }

    private UpdateTimeBlockerRequest getTimeBlockerRequest() {
        return UpdateTimeBlockerRequest.builder()
                .id(1)
                .start_date("2024-11-04")
                .end_date("2024-11-06")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();
    }

    private BarberEntity getBarber() {
        UserEntity user = UserEntity.builder()
                .id(2)
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(true)
                .build();

        return BarberEntity.builder()
                .id(1)
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }
}
