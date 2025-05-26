package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetAppointmentsResponse;

public interface GetAppointmentsUseCase {
    GetAppointmentsResponse getAppointments();

    GetAppointmentsResponse getBarberAppointments(int id);

    GetAppointmentsResponse getBarberAppointmentsByDate(int id, String date);

    GetAppointmentsResponse getBarberAppointmentsBetweenDates(String start_date, String end_date, int barber_id);

    GetAppointmentsResponse getBarberRecentAppointments(int barber_id);

    GetAppointmentsResponse getUserAppointments(int id);
}
