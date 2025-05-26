package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidCredentialsException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.clipfadeapplication.domain.request.LoginRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.LoginResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void loginUser_shouldLoginUserSuccessfullyAndReturnToken() {
        //Arrange
        UserEntity user = getUser();

        LoginRequest request = getRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("mockAccessToken");

        //Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockAccessToken", response.getAccess_token());
    }

    @Test
    void loginUser_shouldLoginUserSuccessfullyAsBarberAndReturnToken() {
        //Arrange
        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);

        LoginRequest request = getRequest();
        user.set_barber(true);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(barberRepository.findByUserId(user.getId())).thenReturn(Optional.of(barber));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("mockAccessToken");

        //Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockAccessToken", response.getAccess_token());
    }

    @Test
    void loginUser_shouldLoginUserSuccessfullyAsAdminAndReturnToken() {
        //Arrange
        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);

        LoginRequest request = getRequest();
        user.set_barber(true);
        barber.set_admin(true);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(barberRepository.findByUserId(user.getId())).thenReturn(Optional.of(barber));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("mockAccessToken");

        //Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockAccessToken", response.getAccess_token());
    }

    @Test
    void loginUser_shouldThrowInvalidUserException_whenUserDoesNotExist() {
        // Arrange
        String userEmail = "janedoe@gmail.com";
        LoginRequest request = getRequest();
        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> loginUseCase.login(request));
    }

    @Test
    void loginUser_shouldThrowInvalidCredentialsException_whenPasswordsDontMatch() {
        // Arrange
        UserEntity user = getUser();

        LoginRequest request = getRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(request));
    }

    @Test
    void loginUser_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        // Arrange
        UserEntity user = getUser();
        user.set_barber(true);

        LoginRequest request = getRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(barberRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidBarberException.class, () -> loginUseCase.login(request));
    }

    private LoginRequest getRequest() {
        return LoginRequest.builder()
                .email("janedoe@gmail.com")
                .password("123")
                .build();
    }

    private BarberEntity getBarber(UserEntity user) {
        return BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }

    private UserEntity getUser() {
        return UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build();
    }
}
