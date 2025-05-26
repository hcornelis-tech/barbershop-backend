package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;
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
class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser_shouldUpdateUser() {
        //Arrange
        UpdateUserRequest request = getUpdateUserRequest();
        UserEntity user = UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("jane@gmail.com")
                .phone_number("123456789")
                .password("janedoe692632")
                .date_time("2020-09-18")
                .build();

        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));

        //Act
        updateUserUseCase.updateUser(request);

        //Arrange
        assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    void updateUser_shouldThrowInvalidUserException_whenUserDoesNotExist() {
        // Arrange
        int userId = 1;
        UpdateUserRequest request = getUpdateUserRequest();
        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> updateUserUseCase.updateUser(request));
    }

    private UpdateUserRequest getUpdateUserRequest() {
        return UpdateUserRequest.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("jane123@gmail.com")
                .phone_number("123456789")
                .build();
    }
}
