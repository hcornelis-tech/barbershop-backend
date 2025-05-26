package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateAppointmentUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.*;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {
    private AppointmentRepository appointmentRepository;
    private ServiceRepository serviceRepository;
    private BarberRepository barberRepository;
    private UserRepository userRepository;

    @Override
    public Appointment updateAppointment(UpdateAppointmentRequest request) {
        Optional<AppointmentEntity> appointmentOptional = appointmentRepository.findById(Long.valueOf(request.getId()));
        if (appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentException("Appointment with id " + request.getId() + " not found");
        }

        Optional<ServiceEntity> serviceOptional = serviceRepository.findById(Long.valueOf(request.getService_id()));
        if (serviceOptional.isEmpty()) {
            throw new InvalidServiceException("Service with id " + request.getService_id() + " not found");
        }

        Optional<BarberEntity> barberOptional = barberRepository.findById(Long.valueOf(request.getBarber_id()));
        if (barberOptional.isEmpty()) {
            throw new InvalidBarberException("Barber with id " + request.getBarber_id() + " not found");
        }

        if (request.isBy_user()) {
            LocalDate date = LocalDate.parse(request.getDate());
            LocalTime time = LocalTime.parse(request.getTime());
            LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);
            LocalDateTime now = LocalDateTime.now();

            if (appointmentDateTime.isBefore(now)) {
                throw new DateTimeInThePastException("Appointment is in the past and cannot be cancelled");
            }
        }

        AppointmentEntity appointment = appointmentOptional.get();

        if (isAvailable(request, appointment)) {
            appointment.setService(serviceOptional.get());
            appointment.setDate(request.getDate());
            appointment.setTime(request.getTime());
            appointment.setStatus(request.getStatus());
        }

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        return AppointmentConverter.convert(savedAppointment);
    }

    private boolean isAvailable(UpdateAppointmentRequest request, AppointmentEntity appointment) {
        if (Objects.equals(appointment.getStatus(), "Cancelled") && (Objects.equals(request.getStatus(), "Approved") || Objects.equals(request.getStatus(), "No Show"))) {
            boolean isAvailable = checkIfAvailable(request);

            if (!isAvailable) {
                throw new TimeSlotNotAvailableException("Cannot change status from Cancelled to " + request.getStatus() + ". The slot is already booked.");
            }
        }

        if (Objects.equals(appointment.getDate(), request.getDate()) && Objects.equals(appointment.getTime(), request.getTime())) {
            return true;
        }

        boolean isAvailable = checkIfAvailable(request);

        if (!isAvailable) {
            throw new TimeSlotNotAvailableException("The selected date and time are already booked.");
        }

        return true;
    }

    private boolean checkIfAvailable(UpdateAppointmentRequest request) {
        return appointmentRepository.checkIfAvailable(request.getDate(), request.getTime(), Long.valueOf(request.getBarber_id()));
    }
}
