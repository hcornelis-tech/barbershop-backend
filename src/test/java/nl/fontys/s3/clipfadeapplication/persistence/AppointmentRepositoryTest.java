package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AppointmentRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    BarberRepository barberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Test
    void save_shouldSaveAppointmentWithAllFields() {
        AppointmentEntity appointment = createAppointment("2024-12-14", "Completed");

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);
        assertNotNull(savedAppointment.getId());

        savedAppointment = entityManager.find(AppointmentEntity.class, savedAppointment.getId());

        assertNotNull(savedAppointment);
        assertEquals(appointment.getDate(), savedAppointment.getDate());
    }

    @Test
    void findAppointmentById_shouldReturnAppointment() {
        AppointmentEntity appointment = createAppointment("2024-12-14", "Completed");

        appointmentRepository.save(appointment);

        Optional<AppointmentEntity> optionalAppointment = appointmentRepository.findAppointmentById(1);

        assertNotNull(optionalAppointment.get());
    }

    @Test
    void findAppointmentsByBarber_shouldReturnAppointments() {
        AppointmentEntity appointment = createAppointment("2024-12-15", "Completed");

        appointmentRepository.save(appointment);

        List<AppointmentEntity> appointments = appointmentRepository.findByBarber(12345678L);

        assertEquals(1, appointments.size());
    }

    @Test
    void checkIfAvailable_shouldReturnFalseBecauseDateAndTimeAlreadyBooked() {
        AppointmentEntity appointment = createAppointment("2024-12-16", "Completed");

        appointmentRepository.save(appointment);

        boolean isAvailable = appointmentRepository.checkIfAvailable("2024-12-16", "12:00", 12345678L);

        assertFalse(isAvailable);
    }

    @Test
    void checkIfAvailable_shouldReturnTrueBecauseDateAndTimeAreAvailable() {
        AppointmentEntity appointment = createAppointment("2024-12-16", "Completed");

        appointmentRepository.save(appointment);

        boolean isAvailable = appointmentRepository.checkIfAvailable("2024-12-17", "12:00", 12345678L);

        assertTrue(isAvailable);
    }

    private AppointmentEntity createAppointment(String dateTime, String status) {
        UserEntity user = UserEntity.builder()
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

        UserEntity savedUser = userRepository.save(user);

        BarberEntity barber = BarberEntity.builder()
                .id(12345678)
                .user(savedUser)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();

        ServiceEntity service = ServiceEntity.builder()
                .id(202)
                .name("test cut")
                .description("test description")
                .price(25.00)
                .duration(30)
                .appointments(Collections.emptyList())
                .build();

        barberRepository.save(barber);
        serviceRepository.save(service);

        return AppointmentEntity.builder()
                .id(1)
                .date(dateTime)
                .time("12:00")
                .status(status)
                .total_price(100)
                .booked_at("2024-01-01")
                .payment_method("Card")
                .booked_online(true)
                .user(savedUser)
                .barber(barber)
                .service(service)
                .build();
    }
}
