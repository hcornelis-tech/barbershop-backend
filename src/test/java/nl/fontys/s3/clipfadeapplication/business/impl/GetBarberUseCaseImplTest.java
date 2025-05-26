package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBarberUseCaseImplTest {
    @Mock
    private BarberRepository barberRepository;

    @InjectMocks
    private GetBarberUseCaseImpl getBarberUseCase;

    @Test
    void getBarber_ShouldReturnBarber() {
        //Arrange
        UserEntity user = createUser();

        BarberEntity barber = BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-25")
                .is_admin(true)
                .build();

        GetBarberResponse expectedResponse = BarberConverter.convert(barber);

        when(barberRepository.findByUserId(11)).thenReturn(Optional.of(barber));

        // Act
        GetBarberResponse response = getBarberUseCase.getBarber(11);

        //Assert
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getFirst_name(), response.getFirst_name());
        assertEquals(expectedResponse.getLast_name(), response.getLast_name());
        assertEquals(expectedResponse.getEmail(), response.getEmail());
    }

    @Test
    void getBarber_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        // Arrange
        int barberId = 1;
        when(barberRepository.findByUserId(barberId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidBarberException.class, () -> getBarberUseCase.getBarber(barberId));
    }

    private UserEntity createUser() {
        return UserEntity.builder()
                .id(11)
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
