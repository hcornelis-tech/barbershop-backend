package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Appointment;

import java.util.Optional;

public interface GetAppointmentUseCase {
    Optional<Appointment> getAppointment(int id);
}
