package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
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
class GetUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void getUser_ShouldReturnUser() {
        //Arrange
        UserEntity user = UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .build();

        GetUserResponse expectedResponse = UserConverter.convert(user);

        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));

        //Act
        GetUserResponse response = getUserUseCase.getUser(1);

        //Assert
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getFirst_name(), response.getFirst_name());
        assertEquals(expectedResponse.getLast_name(), response.getLast_name());
        assertEquals(expectedResponse.getEmail(), response.getEmail());
    }

    @Test
    void getUser_shouldThrowInvalidUserException_whenUserDoesNotExist() {
        // Arrange
        int userId = 1;
        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> getUserUseCase.getUser(userId));
    }
}
