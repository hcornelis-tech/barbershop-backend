package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetAppointmentsResponse;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentsUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void getAppointments_shouldReturnAllAppointments() {
        //Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date("2024-11-25")
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findAllByOrderByBookedAtDesc()).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        //Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getAppointments();

        //Assert
        assertEquals(appointments.size(), response.getAppointments().size());
    }

    @Test
    void getBarberAppointments_shouldReturnAppointmentsForSpecificBarber() {
        // Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date("2024-11-25")
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findByBarber(Long.valueOf(barber.getId()))).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        // Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getBarberAppointments(barber.getId());

        // Assert
        assertEquals(appointments.size(), response.getAppointments().size());
    }

    @Test
    void getBarberAppointmentsByDate_shouldReturnAppointmentsForBarberOnSpecificDate() {
        // Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();
        String date = "2024-11-25";

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date(date)
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findByBarberAndDate(Long.valueOf(barber.getId()), date)).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        // Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getBarberAppointmentsByDate(barber.getId(), date);

        // Assert
        assertEquals(appointments.size(), response.getAppointments().size());
        assertEquals(date, response.getAppointments().get(0).getDate());
    }

    @Test
    void getBarberAppointmentsBetweenDates_shouldReturnAppointmentsForBarberOnSpecificDate() {
        // Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();
        String date = "2024-12-02";
        String startDate = "2024-12-01";
        String endDate = "2024-12-18";

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date(date)
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findAppointmentsBetweenDates(startDate, endDate, barber.getId())).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        //Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getBarberAppointmentsBetweenDates(startDate, endDate, barber.getId());

        // Assert
        assertEquals(appointments.size(), response.getAppointments().size());
        assertEquals(date, response.getAppointments().get(0).getDate());
    }

    @Test
    void getBarberRecentAppointments_shouldReturnRecentAppointmentsForBarber() {
        // Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();
        String date = "2024-12-02";

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date(date)
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findBarberRecentAppointments(Long.valueOf(barber.getId()))).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        //Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getBarberRecentAppointments(barber.getId());

        // Assert
        assertEquals(appointments.size(), response.getAppointments().size());
        assertEquals(date, response.getAppointments().get(0).getDate());
    }

    @Test
    void getUserAppointments_shouldReturnAppointmentsForSpecificUser() {
        // Arrange
        BarberEntity barber = createBarber();
        UserEntity user = createUser();
        ServiceEntity service = createService();
        String date = "2024-12-02";

        List<AppointmentEntity> appointments = List.of(AppointmentEntity.builder()
                .barber(barber)
                .user(user)
                .service(service)
                .date(date)
                .time("11:00")
                .status("Approved")
                .total_price(25)
                .payments(Collections.emptyList())
                .build());

        when(appointmentRepository.findAppointmentsByUser(Long.valueOf(user.getId()))).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        //Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getUserAppointments(user.getId());

        // Assert
        assertEquals(appointments.size(), response.getAppointments().size());
        assertEquals(date, response.getAppointments().get(0).getDate());
    }

    @Test
    void getAppointments_shouldNotReturnAllAppointmentsBecauseOfEmptyList() {
        List<AppointmentEntity> appointments = Collections.emptyList();

        when(appointmentRepository.findAllByOrderByBookedAtDesc()).thenReturn(appointments);

        GetAppointmentsUseCaseImpl getAppointmentsUseCase = new GetAppointmentsUseCaseImpl(appointmentRepository);

        //Act
        GetAppointmentsResponse response = getAppointmentsUseCase.getAppointments();

        //Assert
        assertThat(response.getAppointments().isEmpty());
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
