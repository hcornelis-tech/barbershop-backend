package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidPassword;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdatePasswordRequest;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdatePasswordUseCaseImpl updatePasswordUseCase;

    @Test
    void updatePassword_success() {
        // Arrange
        UpdatePasswordRequest request = getUpdatePasswordRequest();

        UserEntity user = new UserEntity();
        user.setPassword("hashed_current");

        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current", "hashed_current")).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("hashed_new");

        // Act
        updatePasswordUseCase.updatePassword(request);

        // Assert
        verify(userRepository).findById(Long.valueOf(1));
        verify(passwordEncoder).matches("current", "hashed_current");
        verify(passwordEncoder).encode("new");
        verify(userRepository).save(user);
        assertEquals("hashed_new", user.getPassword());
    }

    @Test
    void updatePassword_shouldThrowInvalidUserException_whenUserDoesNotExist() {
        // Arrange
        int userId = 1;
        UpdatePasswordRequest request = getUpdatePasswordRequest();
        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> updatePasswordUseCase.updatePassword(request));
    }

    @Test
    void updatePassword_shouldThrowInvalidPasswordException_whenCurrentPasswordIncorrect() {
        // Arrange
        int userId = 1;
        UserEntity user = new UserEntity();
        UpdatePasswordRequest request = getUpdatePasswordRequest();
        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(InvalidPassword.class, () -> updatePasswordUseCase.updatePassword(request));
    }

    @Test
    void updatePassword_shouldThrowInvalidPasswordException_whenPasswordsDontMatch() {
        // Arrange
        int userId = 1;
        UserEntity user = new UserEntity();
        UpdatePasswordRequest request = getUpdatePasswordRequest();
        request.setConfirm_new_password("yh");

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(InvalidPassword.class, () -> updatePasswordUseCase.updatePassword(request));
    }


    private UpdatePasswordRequest getUpdatePasswordRequest() {
        return UpdatePasswordRequest.builder()
                .id(1)
                .current_password("current")
                .new_password("new")
                .confirm_new_password("new")
                .build();
    }
}
