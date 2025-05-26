package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetBarbersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBarbersUseCaseImplTest {
    @Mock
    private BarberRepository barberRepository;

    @Test
    void getBarbers_shouldReturnAllBarbers() {
        //Arrange
        UserEntity user = createUser();

        List<BarberEntity> barbers = List.of(BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-25")
                .is_admin(true)
                .build());

        when(barberRepository.findAll()).thenReturn(barbers);

        GetBarbersUseCaseImpl getBarbersUseCase = new GetBarbersUseCaseImpl(barberRepository);

        //Act
        GetBarbersResponse response = getBarbersUseCase.getBarbers();

        //Assert
        assertEquals(barbers.size(), response.getBarbers().size());
    }

    @Test
    void getBarbers_shouldNotReturnAllBarbersBecauseOfEmptyList() {
        //Arrange
        List<BarberEntity> barbers = Collections.emptyList();

        when(barberRepository.findAll()).thenReturn(barbers);

        GetBarbersUseCaseImpl getBarbersUseCase = new GetBarbersUseCaseImpl(barberRepository);

        //Act
        GetBarbersResponse response = getBarbersUseCase.getBarbers();

        //Assert
        assertEquals(barbers.size(), response.getBarbers().size());
    }

    private UserEntity createUser() {
        return UserEntity.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("boblee@gmail.com")
                .phone_number("123")
                .password("123")
                .is_barber(true)
                .date_time("2024-11-26")
                .build();
    }
}
