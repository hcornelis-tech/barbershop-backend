package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.CreateUserUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBarberUseCaseImplTest {
    @InjectMocks
    private CreateBarberUseCaseImpl createBarberUseCase;

    @Mock
    private GenerateRandomId generateRandomId;

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Test
    void createBarber_shouldReturnBarberCreatedSuccessfully() {
        //Arrange
        CreateBarberRequest request = createBarberRequest();

        UserEntity user = UserEntity.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("boblee@gmail.com")
                .phone_number("123")
                .password("123")
                .is_barber(true)
                .date_time("2024-11-26")
                .build();

        BarberEntity barber = BarberEntity.builder()
                .id(1)
                .user(user)
                .hire_date("2024-11-25")
                .is_admin(true)
                .build();

        when(createUserUseCase.saveNewUser(any(CreateUserRequest.class))).thenReturn(user);
        when(barberRepository.save(any(BarberEntity.class))).thenReturn(barber);
        when(generateRandomId.generate(anyInt(), anyInt())).thenReturn(12345);

        //Act
        CreateBarberResponse response = createBarberUseCase.createBarber(request);

        //Assert
        verify(createUserUseCase).saveNewUser(any(CreateUserRequest.class));
        verify(barberRepository).save(any(BarberEntity.class));
        assertEquals(user.getId(), barber.getUser().getId());
        assertEquals(user.getFirst_name(), barber.getUser().getFirst_name());
        assertEquals(1, response.getId());
    }

    private CreateBarberRequest createBarberRequest() {
        return CreateBarberRequest.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("boblee@gmail.com")
                .phone_number("123")
                .password("123")
                .is_barber(true)
                .date_time("2024-11-26")
                .hire_date("2024-11-25")
                .is_admin(true)
                .build();
    }
}
