package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.LoginUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.EmailAlreadyExists;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidEmailAddress;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidPassword;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidPhoneNumber;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GenerateRandomId generateRandomId;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Mock
    private LoginUseCase loginUseCase;

    @Test
    void createCustomer_shouldReturnCustomerCreatedSuccessfully() {
        // Arrange
        CreateUserRequest request = createTestRequest();

        UserEntity expectedUser = UserEntity.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(true)
                .appointments(Collections.emptyList())
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUser);
        when(generateRandomId.generate(anyInt(), anyInt())).thenReturn(12345);

        // Act
        CreateUserResponse actualUser = createUserUseCase.createUser(request);

        // Assertions
        assertEquals(expectedUser.getId(), actualUser.getId());
    }

    @Test
    void createCustomer_shouldThrowEmailAlreadyExists_whenEmailAlreadyExists() {
        // Arrange
        CreateUserRequest request = createTestRequest();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExists.class, () -> createUserUseCase.createUser(request));
    }

    @Test
    void createCustomer_shouldThrowInvalidPhoneNumber_whenPhoneNumberIsInvalid() {
        // Arrange
        CreateUserRequest request = createTestRequest();
        request.setPhone_number("invalid-phone");

        // Act & Assert
        assertThrows(InvalidPhoneNumber.class, () -> createUserUseCase.createUser(request));
    }

    @Test
    void createCustomer_shouldThrowInvalidEmailAddress_whenEmailAddressIsInvalid() {
        // Arrange
        CreateUserRequest request = createTestRequest();
        request.setEmail("email");

        // Act & Assert
        assertThrows(InvalidEmailAddress.class, () -> createUserUseCase.createUser(request));
    }

    @Test
    void createCustomer_shouldThrowInvalidPassword_whenPasswordsDontMatch() {
        // Arrange
        CreateUserRequest request = createTestRequest();
        request.setPassword("invalid-password");

        // Act & Assert
        assertThrows(InvalidPassword.class, () -> createUserUseCase.createUser(request));
    }

    private CreateUserRequest createTestRequest() {
        return CreateUserRequest.builder()
                .first_name("Jane")
                .last_name("Doe")
                .email("jane@gmail.com")
                .phone_number("123456789")
                .password("janedoe692632")
                .confirm_password("janedoe692632")
                .date_time("2020-09-18")
                .build();
    }
}
