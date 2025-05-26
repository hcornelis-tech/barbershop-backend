package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTimeBlockersUseCaseTest {
    @Mock
    private TimeBlockerRepository timeBlockerRepository;

    @Test
    void getTimeBlockers_shouldReturnAllTimeBlockersByBarber() {
        //Arrange
        BarberEntity barber = getBarber();

        List<TimeBlockerEntity> expectedTimeBlockers = List.of(TimeBlockerEntity.builder()
                .id(1)
                .barber(barber)
                .start_date("2024-12-05")
                .end_date("2024-12-05")
                .start_time("10:00")
                .end_time("13:00")
                .all_day(true)
                .build());

        when(timeBlockerRepository.findByBarberId(1)).thenReturn(expectedTimeBlockers);

        GetTimeBlockersUseCaseImpl getTimeBlockersUseCase = new GetTimeBlockersUseCaseImpl(timeBlockerRepository);

        //Act
        GetTimeBlockersResponse response = getTimeBlockersUseCase.getTimeBlockersByBarber(1);

        //Assert
        Assertions.assertEquals(expectedTimeBlockers.size(), response.getTimeblockers().size());
    }

    @Test
    void getTimeBlockers_shouldNotReturnAllTimeBlockersByBarberBecauseOfEmptyList() {
        //Arrange
        List<TimeBlockerEntity> expectedTimeBlockers = Collections.emptyList();

        when(timeBlockerRepository.findByBarberId(1)).thenReturn(expectedTimeBlockers);

        GetTimeBlockersUseCaseImpl getTimeBlockersUseCase = new GetTimeBlockersUseCaseImpl(timeBlockerRepository);

        //Act
        GetTimeBlockersResponse response = getTimeBlockersUseCase.getTimeBlockersByBarber(1);

        //Assert
        Assertions.assertEquals(expectedTimeBlockers.size(), response.getTimeblockers().size());
    }

    private BarberEntity getBarber() {
        UserEntity user = UserEntity.builder()
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
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }
}
