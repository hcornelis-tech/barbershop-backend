package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateAppointmentUseCase;
import nl.fontys.s3.clipfadeapplication.business.CreatePaymentUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.*;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.persistence.*;
import nl.fontys.s3.clipfadeapplication.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {
    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private BarberRepository barberRepository;
    private ServiceRepository serviceRepository;
    private ScheduleRepository scheduleRepository;
    private final GenerateRandomId generateRandomId;
    private CreatePaymentUseCase createPaymentUseCase;

    @Override
    public Appointment createAppointment(CreateAppointmentRequest request) {
        LocalDate selectedDate = LocalDate.parse(request.getDate());
        DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();

        ScheduleEntity schedule = getSchedule();

        boolean isCompanyOpen =
                (dayOfWeek == DayOfWeek.MONDAY && schedule.isMonday()) ||
                        (dayOfWeek == DayOfWeek.TUESDAY && schedule.isTuesday()) ||
                        (dayOfWeek == DayOfWeek.WEDNESDAY && schedule.isWednesday()) ||
                        (dayOfWeek == DayOfWeek.THURSDAY && schedule.isThursday()) ||
                        (dayOfWeek == DayOfWeek.FRIDAY && schedule.isFriday()) ||
                        (dayOfWeek == DayOfWeek.SATURDAY && schedule.isSaturday()) ||
                        (dayOfWeek == DayOfWeek.SUNDAY && schedule.isSunday());

        if (!appointmentRepository.checkIfAvailable(request.getDate(), request.getTime(), Long.valueOf(request.getBarber_id()))) {
            throw new TimeSlotNotAvailableException("The selected date and time are already booked.");
        }

        if (!isCompanyOpen) {
            throw new TimeSlotNotAvailableException("The selected date and time are not available.");
        }

        AppointmentEntity appointmentEntity = saveAppointment(request);

        if ("card".equals(appointmentEntity.getPayment_method())) {
            appointmentEntity.setPayments(List.of(createPayment(request, appointmentEntity.getId())));
        }

        return AppointmentConverter.convert(appointmentEntity);
    }

    private AppointmentEntity saveAppointment(CreateAppointmentRequest request) {
        Optional<UserEntity> user = userRepository.findById(Long.valueOf(request.getUser_id()));
        if (user.isEmpty()) {
            throw new InvalidUserException("User not found");
        }

        Optional<BarberEntity> barber = barberRepository.findById(Long.valueOf(request.getBarber_id()));
        if (barber.isEmpty()) {
            throw new InvalidBarberException("Barber not found");
        }

        Optional<ServiceEntity> service = serviceRepository.findById(Long.valueOf(request.getService_id()));
        if (service.isEmpty()) {
            throw new InvalidServiceException("Service not found");
        }

        int randomId = generateUniqueRandomId();

        AppointmentEntity appointment = AppointmentEntity.builder()
                .id(randomId)
                .user(user.get())
                .barber(barber.get())
                .service(service.get())
                .date(request.getDate())
                .time(request.getTime())
                .status(request.getStatus())
                .total_price(request.getTotal_price())
                .booked_at(request.getBooked_at())
                .payment_method(request.getPayment())
                .payments(Collections.emptyList())
                .booked_online(request.isBooked_online())
                .build();

        return appointmentRepository.save(appointment);
    }

    private int generateUniqueRandomId() {
        int randomId;

        do {
            randomId = generateRandomId.generate(100000000, 900000000);
        } while (userRepository.existsById(randomId));

        return randomId;
    }

    private PaymentEntity createPayment(CreateAppointmentRequest request, int appointmentId) {
        CreatePaymentRequest paymentRequest = CreatePaymentRequest.builder()
                .amount(request.getTotal_price())
                .method("Credit Card")
                .build();

        return createPaymentUseCase.createPayment(paymentRequest, appointmentId);
    }

    private ScheduleEntity getSchedule() {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(Long.valueOf(1));
        if (schedule.isEmpty()) {
            throw new RuntimeException("Schedule not found!");
        }

        return schedule.get();
    }
}
