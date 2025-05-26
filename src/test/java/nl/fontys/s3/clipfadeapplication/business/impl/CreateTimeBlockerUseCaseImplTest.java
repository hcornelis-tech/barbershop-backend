package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTimeBlockerUseCaseImplTest {
    @Mock
    private TimeBlockerRepository timeBlockerRepository;

    @Mock
    private GenerateRandomId generateRandomId;

    @Mock
    private BarberRepository barberRepository;

    @InjectMocks
    private CreateTimeBlockerUseCaseImpl createTimeBlockerUseCase;

    @Test
    void createTimeBlocker_shouldCreateTimeBlockerSuccessfully() {
        //Arrange
        CreateTimeBlockerRequest request = getTimeBlockerRequest();
        BarberEntity barber = getBarber();

        TimeBlockerEntity expectedTimeBlocker = TimeBlockerEntity.builder()
                .id(1234)
                .barber(barber)
                .start_date("2024-11-04")
                .end_date("2024-11-04")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();

        when(barberRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(barber));
        when(timeBlockerRepository.save(any(TimeBlockerEntity.class))).thenReturn(expectedTimeBlocker);
        when(generateRandomId.generate(anyInt(), anyInt())).thenReturn(1234);

        //Act
        CreateTimeBlockerResponse response = createTimeBlockerUseCase.CreateTimeBlocker(request);

        // Assertions
        assertEquals(expectedTimeBlocker.getId(), response.getId());
    }

    @Test
    void createTimeBlocker_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        //Arrange
        CreateTimeBlockerRequest request = getTimeBlockerRequest();

        when(barberRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        // Assertions
        assertThrows(InvalidBarberException.class, () -> createTimeBlockerUseCase.CreateTimeBlocker(request));
    }

    @Test
    void createTimeBlocker_shouldThrowDateTimeInThePastException_whenTimeEndDateIsBeforeStartDate() {
        // Arrange
        CreateTimeBlockerRequest request = getTimeBlockerRequest();
        request.setEnd_date("2024-11-01");

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> createTimeBlockerUseCase.CreateTimeBlocker(request));
    }

    @Test
    void createTimeBlocker_shouldThrowDateTimeInThePastException_whenTimeEndTimeIsBeforeStartTime() {
        // Arrange
        CreateTimeBlockerRequest request = getTimeBlockerRequest();
        request.setEnd_time("09:00");

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> createTimeBlockerUseCase.CreateTimeBlocker(request));
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
                .appointments(Collections.emptyList())
                .build();

        return BarberEntity.builder()
                .id(1)
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }

    private CreateTimeBlockerRequest getTimeBlockerRequest() {
        return CreateTimeBlockerRequest.builder()
                .barber_id(1)
                .start_date("2024-11-04")
                .end_date("2024-11-04")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();
    }
}
