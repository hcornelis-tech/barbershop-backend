package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetAppointmentUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAppointmentUseCaseImpl implements GetAppointmentUseCase {
    private AppointmentRepository appointmentRepository;

    public Optional<Appointment> getAppointment(int id) {
        return appointmentRepository.findAppointmentById(Long.valueOf(id)).map(AppointmentConverter::convert);
    }
}
