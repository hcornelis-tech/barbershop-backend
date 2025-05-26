package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetUsersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void getUsers_shouldReturnAllUsers() {
        //Arrange

        List<UserEntity> expectedUsers = List.of(UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build());

        when(userRepository.findAll()).thenReturn(expectedUsers);

        GetUsersUseCaseImpl getUsersUseCase = new GetUsersUseCaseImpl(userRepository, appointmentRepository);

        //Act
        GetUsersResponse response = getUsersUseCase.getUsers();

        //Assert
        assertEquals(expectedUsers.size(), response.getUsers().size());
    }

    @Test
    void getUsers_shouldNotReturnAllUsersBecauseOfEmptyList() {
        List<UserEntity> expectedUsers = Collections.emptyList();

        when(userRepository.findAll()).thenReturn(expectedUsers);

        GetUsersUseCaseImpl getUsersUseCase = new GetUsersUseCaseImpl(userRepository, appointmentRepository);

        //Act
        GetUsersResponse response = getUsersUseCase.getUsers();

        //Assert
        assertThat(response.getUsers().isEmpty());
    }

    @Test
    void getUsersByBarber_shouldReturnAllUsers() {
        //Arrange

        List<UserEntity> expectedUsers = List.of(UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build());

        when(userRepository.findByBarber(Long.valueOf(1))).thenReturn(expectedUsers);

        GetUsersUseCaseImpl getUsersUseCase = new GetUsersUseCaseImpl(userRepository, appointmentRepository);

        //Act
        GetUsersResponse response = getUsersUseCase.getUsersByBarber(1);

        //Assert
        assertEquals(expectedUsers.size(), response.getUsers().size());
    }
}
