package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateAppointmentRequest;

public interface UpdateAppointmentUseCase {
    Appointment updateAppointment(UpdateAppointmentRequest request);
}
