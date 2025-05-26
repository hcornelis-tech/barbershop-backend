package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.DeleteAppointmentUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAppointmentUseCaseImpl implements DeleteAppointmentUseCase {
    private AppointmentRepository appointmentRepository;

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(Long.valueOf(id));
    }
}
