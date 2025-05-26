package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateAppointmentRequest;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(CreateAppointmentRequest request);
}
