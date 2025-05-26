package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.DeleteUserUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteBarberUseCaseImplTest {

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private DeleteBarberUseCaseImpl deleteBarberUseCase;

    @Test
    void deleteBarber_callsRepositoryDeleteById_andDeleteUser_whenBarberExists() {
        // Arrange
        int barberId = 1;
        BarberEntity barberEntity = BarberEntity.builder()
                .id(barberId)
                .user(UserEntity.builder().id(123).build())
                .build();

        when(barberRepository.findById(Long.valueOf(barberId))).thenReturn(Optional.of(barberEntity));
        when(barberRepository.existsById(Long.valueOf(barberId))).thenReturn(false);
        // Act
        deleteBarberUseCase.deleteBarber(barberId);

        // Assert
        verify(barberRepository).deleteById(Long.valueOf(barberId));
        verify(deleteUserUseCase).deleteUser(123);
    }

    @Test
    void deleteBarber_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        // Arrange
        int barberId = 1;
        when(barberRepository.findById(Long.valueOf(barberId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidBarberException.class, () -> deleteBarberUseCase.deleteBarber(barberId));
    }

    @Test
    void deleteBarber_callsRepositoryDeleteById_andDeleteUser_whenBarberDoesNotExists() {
        // Arrange
        int barberId = 1;
        BarberEntity barberEntity = BarberEntity.builder()
                .id(barberId)
                .user(UserEntity.builder().id(123).build())
                .build();

        when(barberRepository.findById(Long.valueOf(barberId))).thenReturn(Optional.of(barberEntity));
        when(barberRepository.existsById(Long.valueOf(barberId))).thenReturn(true);

        // Act
        deleteBarberUseCase.deleteBarber(barberId);

        // Assert
        verify(barberRepository).deleteById(Long.valueOf(barberId));
    }

}
