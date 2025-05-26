package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void getAppointment_shouldReturnAppointment() {
        //Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();

        AppointmentEntity appointment = AppointmentEntity.builder()
                .id(1)
                .barber(barber)
                .user(user)
                .service(service)
                .date("2024-11-25")
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build();

        when(appointmentRepository.findAppointmentById(Long.valueOf(1))).thenReturn(Optional.of(appointment));

        GetAppointmentUseCaseImpl getAppointmentUseCase = new GetAppointmentUseCaseImpl(appointmentRepository);

        //Act
        Optional<Appointment> response = getAppointmentUseCase.getAppointment(1);

        //Assert
        assertEquals(appointment.getId(), response.get().getId());
        assertEquals(appointment.getDate(), response.get().getDate());
        assertEquals(appointment.getTime(), response.get().getTime());
    }

    private UserEntity createUser() {
        return UserEntity.builder()
                .first_name("Jane")
                .last_name("Doe")
                .email("jane@gmail.com")
                .phone_number("123456789")
                .password("janedoe692632")
                .date_time("2020-09-18")
                .build();
    }

    private BarberEntity createBarber() {
        UserEntity user = createUser();

        return BarberEntity.builder()
                .id(1)
                .user(user)
                .hire_date("2024-11-25")
                .is_admin(true)
                .build();
    }

    private ServiceEntity createService() {
        return ServiceEntity.builder()
                .name("Full Cut")
                .description("Hello")
                .price(25.0)
                .build();
    }
}
