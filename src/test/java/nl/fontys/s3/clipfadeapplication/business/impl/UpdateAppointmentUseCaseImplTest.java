package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.*;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private BarberRepository barberRepository;

    @InjectMocks
    private UpdateAppointmentUseCaseImpl updateAppointmentUseCase;

    @Test
    void updateAppointment_shouldUpdateSuccessfully_whenDataIsValid() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();
        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);
        ServiceEntity service = getService();
        AppointmentEntity appointment = createAppointmentEntity(user, barber, service);

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.of(appointment));
        when(serviceRepository.findById(Long.valueOf(request.getService_id()))).thenReturn(Optional.of(service));
        when(barberRepository.findById(Long.valueOf(request.getBarber_id()))).thenReturn(Optional.of(barber));
        when(appointmentRepository.checkIfAvailable(request.getDate(), request.getTime(), Long.valueOf(request.getBarber_id()))).thenReturn(true);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        // Act
        Appointment result = updateAppointmentUseCase.updateAppointment(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getDate(), result.getDate());
        assertEquals(request.getTime(), result.getTime());
        assertEquals(request.getStatus(), result.getStatus());
    }

    @Test
    void updateAppointment_shouldThrowInvalidAppointmentException_whenAppointmentDoesNotExist() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidAppointmentException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    void updateAppointment_shouldThrowInvalidServiceException_whenServiceDoesNotExist() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();
        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);
        ServiceEntity service = getService();
        AppointmentEntity appointment = createAppointmentEntity(user, barber, service);

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.of(appointment));
        when(serviceRepository.findById(Long.valueOf(request.getService_id()))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidServiceException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    void updateAppointment_shouldThrowInvalidBarberException_whenBarberDoesNotExist() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();
        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);
        ServiceEntity service = getService();
        AppointmentEntity appointment = createAppointmentEntity(user, barber, service);

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.of(appointment));
        when(serviceRepository.findById(Long.valueOf(request.getService_id()))).thenReturn(Optional.of(service));
        when(barberRepository.findById(Long.valueOf(request.getBarber_id()))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidBarberException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    void updateAppointment_shouldThrowDateTimeInThePastException_whenAppointmentIsInThePast() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();
        request.setDate(LocalDate.now().minusDays(1).toString());
        request.setTime(LocalTime.now().toString());

        request.setBy_user(true);

        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);
        ServiceEntity service = getService();
        AppointmentEntity appointment = createAppointmentEntity(user, barber, service);

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.of(appointment));
        when(serviceRepository.findById(Long.valueOf(request.getService_id()))).thenReturn(Optional.of(service));
        when(barberRepository.findById(Long.valueOf(request.getBarber_id()))).thenReturn(Optional.of(barber));

        // Act & Assert
        assertThrows(DateTimeInThePastException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    void updateAppointment_shouldThrowTimeSlotNotAvailableException_whenSlotIsAlreadyBooked() {
        // Arrange
        UpdateAppointmentRequest request = createUpdateAppointmentRequest();
        request.setStatus("Approved");

        UserEntity user = getUser();
        BarberEntity barber = getBarber(user);
        ServiceEntity service = getService();
        AppointmentEntity appointment = createAppointmentEntity(user, barber, service);
        appointment.setStatus("Cancelled");

        when(appointmentRepository.findById(Long.valueOf(request.getId()))).thenReturn(Optional.of(appointment));
        when(serviceRepository.findById(Long.valueOf(request.getService_id()))).thenReturn(Optional.of(service));
        when(barberRepository.findById(Long.valueOf(request.getBarber_id()))).thenReturn(Optional.of(barber));
        when(appointmentRepository.checkIfAvailable(request.getDate(), request.getTime(), Long.valueOf(request.getBarber_id()))).thenReturn(false);

        // Act & Assert
        assertThrows(TimeSlotNotAvailableException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    private UpdateAppointmentRequest createUpdateAppointmentRequest() {
        return UpdateAppointmentRequest.builder()
                .id(23)
                .barber_id(1)
                .service_id(1)
                .status("No Show")
                .date(LocalDate.now().plusDays(2).toString())
                .time(LocalTime.of(11, 0).toString())
                .by_user(true)
                .build();
    }

    private UserEntity getUser() {
        return UserEntity.builder()
                .id(17282)
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

    private BarberEntity getBarber(UserEntity user) {
        return BarberEntity.builder()
                .id(12345678)
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }

    private ServiceEntity getService() {
        return ServiceEntity.builder()
                .id(202)
                .name("test cut")
                .description("test description")
                .price(25.00)
                .duration(30)
                .appointments(Collections.emptyList())
                .build();
    }

    private AppointmentEntity createAppointmentEntity(UserEntity user, BarberEntity barber, ServiceEntity service) {
        return AppointmentEntity.builder()
                .id(1)
                .date("2024-12-12")
                .time("12:00")
                .status("Approved")
                .total_price(100)
                .booked_at("2024-01-01")
                .payment_method("Card")
                .booked_online(true)
                .user(user)
                .barber(barber)
                .service(service)
                .payments(Collections.emptyList())
                .build();
    }
}
