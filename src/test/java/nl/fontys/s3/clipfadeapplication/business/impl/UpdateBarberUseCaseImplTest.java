package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBarberUseCaseImplTest {
    @Mock
    private BarberRepository barberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UpdateUserUseCaseImpl updateUserUseCase;

    @InjectMocks
    private UpdateBarberUseCaseImpl updateBarberUseCase;

    @Test
    void updateBarber_shouldUpdateBarber() {
        //Arrange
        UpdateBarberRequest request = getUpdateBarberRequest();
        UserEntity user = getUser();

        BarberEntity barber = BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();

        when(barberRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(barber));

        //Act
        updateBarberUseCase.updateBarber(request);

        //Assert
        assertEquals(request.getFirst_name(), barber.getUser().getFirst_name());
    }

    @Test
    void updateBarber_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        // Arrange
        UpdateBarberRequest request = getUpdateBarberRequest();

        // Act & Assert
        assertThrows(InvalidBarberException.class, () -> updateBarberUseCase.updateBarber(request));
    }

    private UpdateBarberRequest getUpdateBarberRequest() {
        return UpdateBarberRequest.builder()
                .id(1)
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }

    private UserEntity getUser() {
        return UserEntity.builder()
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
    }
}
