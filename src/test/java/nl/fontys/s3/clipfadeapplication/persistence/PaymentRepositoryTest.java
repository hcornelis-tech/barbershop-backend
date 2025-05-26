package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PaymentRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    BarberRepository barberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Test
    void save_shouldSavePaymentWithAllFields() {
        AppointmentEntity appointment = createAppointment("2024-12-05", "Completed");
        appointmentRepository.save(appointment);

        PaymentEntity payment = getPayment("2024-12-05", appointment);

        PaymentEntity savedPayment = paymentRepository.save(payment);
        assertNotNull(savedPayment.getId());

        savedPayment = entityManager.find(PaymentEntity.class, savedPayment.getId());

        PaymentEntity expectedPayment = getPayment("2024-12-05", appointment);

        assertEquals(expectedPayment, savedPayment);
    }

    @Test
    void findPaymentsBetweenDates_shouldReturnAppointments() {
        AppointmentEntity appointment1 = createAppointment("2024-12-05", "Completed");
        AppointmentEntity appointment2 = createAppointment("2024-12-06", "Cancelled");
        AppointmentEntity appointment3 = createAppointment("2024-12-07", "Completed");

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        PaymentEntity payment1 = getPayment("1736876617891", appointment1);
        PaymentEntity payment2 = getPayment("1736848107692", appointment2);
        PaymentEntity payment3 = getPayment("1736211512702", appointment3);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        String startDate = "1736211512702";
        String endDate = "1736876617891";

        List<PaymentEntity> payments = paymentRepository.findPaymentsBetweenDates(startDate, endDate);

        assertEquals(1, payments.size());
        assertEquals(payment2.getId(), payments.get(0).getId());
    }

    @Test
    void findPaymentsBetweenDatesByBarber_shouldReturnAppointments() {
        AppointmentEntity appointment1 = createAppointment("2024-12-05", "Completed");
        AppointmentEntity appointment2 = createAppointment("2024-12-06", "Completed");
        AppointmentEntity appointment3 = createAppointment("2024-12-07", "Completed");

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        PaymentEntity payment1 = getPayment("1736876617891", appointment1);
        PaymentEntity payment2 = getPayment("1736848107692", appointment2);
        PaymentEntity payment3 = getPayment("1736211512702", appointment3);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        String startDate = "1736211512702";
        String endDate = "1736876617891";

        List<PaymentEntity> payments = paymentRepository.findPaymentsBetweenDatesByBarber(startDate, endDate, 12345678);

        assertEquals(1, payments.size());
        assertEquals(payment2.getId(), payments.get(0).getId());
    }

    private PaymentEntity getPayment(String dateTime, AppointmentEntity appointment) {
        return PaymentEntity.builder()
                .id("1")
                .amount(20)
                .date_time(dateTime)
                .method("Card")
                .status("Completed")
                .appointment(appointment)
                .build();
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
