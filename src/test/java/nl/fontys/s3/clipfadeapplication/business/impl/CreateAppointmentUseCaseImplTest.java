package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.CreatePaymentUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.TimeSlotNotAvailableException;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.persistence.*;
import nl.fontys.s3.clipfadeapplication.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentUseCaseImplTest {
    @InjectMocks
    private CreateAppointmentUseCaseImpl createAppointmentUseCase;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private GenerateRandomId generateRandomId;

    @Mock
    private CreatePaymentUseCase createPaymentUseCase;

    @Test
    void createAppointment_shouldCreateAppointmentSuccessfully() {
        // Arrange
        ScheduleEntity schedule = createSchedule();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirst_name("John");

        UserEntity barberUserEntity = new UserEntity();
        barberUserEntity.setId(2);
        barberUserEntity.setFirst_name("Bob");

        BarberEntity barberEntity = new BarberEntity();
        barberEntity.setId(2);
        barberEntity.setUser(barberUserEntity);

        ServiceEntity serviceEntity = new ServiceEntity();

        CreateAppointmentRequest request = createAppointmentRequest();
        AppointmentEntity appointmentEntity = AppointmentEntity
                .builder()
                .id(123456789)
                .user(userEntity)
                .barber(barberEntity)
                .service(serviceEntity)
                .date("2023-11-01")
                .time("10:00")
                .status("Approved")
                .total_price(50.0)
                .payment_method("at_location")
                .payments(Collections.emptyList())
                .build();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));
        when(serviceRepository.findById(Long.valueOf(3))).thenReturn(Optional.of(serviceEntity));
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userEntity));
        when(barberRepository.findById(Long.valueOf(2))).thenReturn(Optional.of(barberEntity));
        when(appointmentRepository.checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2))).thenReturn(true);
        when(generateRandomId.generate(100000000, 900000000)).thenReturn(123456789);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        // Act
        Appointment response = createAppointmentUseCase.createAppointment(request);

        // Assert
        verify(appointmentRepository).save(any(AppointmentEntity.class));
        verify(appointmentRepository).checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2));
        verify(generateRandomId).generate(100000000, 900000000);
        verify(userRepository).findById(Long.valueOf(1));
        verify(barberRepository).findById(Long.valueOf(2));
        verify(serviceRepository).findById(Long.valueOf(3));


        assertEquals(123456789, response.getId());
    }

    @Test
    void createAppointment_shouldCreateAppointmentWithPaymentSuccessfully() {
        // Arrange
        ScheduleEntity schedule = createSchedule();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirst_name("John");

        UserEntity barberUserEntity = new UserEntity();
        barberUserEntity.setId(2);
        barberUserEntity.setFirst_name("Bob");

        BarberEntity barberEntity = new BarberEntity();
        barberEntity.setId(2);
        barberEntity.setUser(barberUserEntity);

        ServiceEntity serviceEntity = new ServiceEntity();
        PaymentEntity payment = new PaymentEntity();

        CreateAppointmentRequest request = createAppointmentRequest();
        AppointmentEntity appointmentEntity = AppointmentEntity
                .builder()
                .id(123456789)
                .user(userEntity)
                .barber(barberEntity)
                .service(serviceEntity)
                .date("2023-11-01")
                .time("10:00")
                .status("Approved")
                .total_price(50.0)
                .payment_method("card")
                .payments(Collections.emptyList())
                .build();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));
        when(serviceRepository.findById(Long.valueOf(3))).thenReturn(Optional.of(serviceEntity));
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userEntity));
        when(barberRepository.findById(Long.valueOf(2))).thenReturn(Optional.of(barberEntity));
        when(appointmentRepository.checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2))).thenReturn(true);
        when(generateRandomId.generate(100000000, 900000000)).thenReturn(123456789);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);
        when(createPaymentUseCase.createPayment(any(CreatePaymentRequest.class), anyInt())).thenReturn(payment);

        // Act
        Appointment response = createAppointmentUseCase.createAppointment(request);

        // Assert
        verify(appointmentRepository).save(any(AppointmentEntity.class));
        verify(appointmentRepository).checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2));
        verify(generateRandomId).generate(100000000, 900000000);
        verify(userRepository).findById(Long.valueOf(1));
        verify(barberRepository).findById(Long.valueOf(2));
        verify(serviceRepository).findById(Long.valueOf(3));


        assertEquals(123456789, response.getId());
    }

    @Test
    void createAppointment_shouldThrowTimeSlotNotAvailableExceptionWhenTimeSlotIsTaken() {
        // Arrange
        CreateAppointmentRequest request = createAppointmentRequest();
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .id(1)
                .monday(true)
                .build();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(scheduleEntity));
        when(appointmentRepository.checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2))).thenReturn(false);

        // Act & Assert
        assertThrows(TimeSlotNotAvailableException.class, () -> createAppointmentUseCase.createAppointment(request));
        verify(appointmentRepository).checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2));
    }

    @Test
    void createAppointment_shouldGenerateUniqueIdWhenConflictOccurs() {
        // Arrange
        CreateAppointmentRequest request = createAppointmentRequest();
        ScheduleEntity schedule = createSchedule();
        ServiceEntity serviceEntity = new ServiceEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirst_name("John");

        UserEntity barberUserEntity = new UserEntity();
        barberUserEntity.setId(2);
        barberUserEntity.setFirst_name("Boblee");

        BarberEntity barberEntity = new BarberEntity();
        barberEntity.setId(2);
        barberEntity.setUser(barberUserEntity);

        AppointmentEntity savedAppointmentEntity = AppointmentEntity.builder()
                .id(987654321)
                .user(userEntity)
                .barber(barberEntity)
                .service(serviceEntity)
                .date("2023-11-01")
                .time("10:00")
                .status("Approved")
                .total_price(50.0)
                .payment_method("at_location")
                .payments(Collections.emptyList())
                .build();

        when(serviceRepository.findById(Long.valueOf(3))).thenReturn(Optional.of(serviceEntity));
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userEntity));
        when(barberRepository.findById(Long.valueOf(2))).thenReturn(Optional.of(barberEntity));
        when(appointmentRepository.checkIfAvailable("2023-11-01", "10:00", Long.valueOf(2))).thenReturn(true);
        when(generateRandomId.generate(100000000, 900000000)).thenReturn(123456789, 987654321);
        when(userRepository.existsById(123456789)).thenReturn(true);
        when(userRepository.existsById(987654321)).thenReturn(false);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(savedAppointmentEntity);
        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));


        // Act
        Appointment response = createAppointmentUseCase.createAppointment(request);

        // Assert
        assertEquals(987654321, response.getId());
    }

    @Test
    void createAppointment_shouldThrowRuntimeException_WhenScheduleDoesNotExist() {
        // Arrange
        CreateAppointmentRequest request = createAppointmentRequest();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> createAppointmentUseCase.createAppointment(request));
    }

    @Test
    void createAppointment_shouldThrowTimeSlotNotAvailable_WhenIsNotOpen() {
        // Arrange
        CreateAppointmentRequest request = createAppointmentRequest();
        ScheduleEntity schedule = createSchedule();

        when(scheduleRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(schedule));

        // Act & Assert
        assertThrows(TimeSlotNotAvailableException.class, () -> createAppointmentUseCase.createAppointment(request));
    }

    private CreateAppointmentRequest createAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .user_id(1)
                .barber_id(2)
                .service_id(3)
                .date("2023-11-01")
                .time("10:00")
                .status("Approved")
                .total_price(50.0)
                .build();
    }

    private ScheduleEntity createSchedule() {
        return ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(30)
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .friday(true)
                .saturday(true)
                .sunday(false)
                .build();
    }
}
