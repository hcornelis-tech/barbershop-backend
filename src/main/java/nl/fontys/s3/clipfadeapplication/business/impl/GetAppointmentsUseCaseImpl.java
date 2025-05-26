package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetAppointmentsUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.response.GetAppointmentsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAppointmentsUseCaseImpl implements GetAppointmentsUseCase {
    private AppointmentRepository appointmentRepository;

    @Override
    public GetAppointmentsResponse getAppointments() {
        List<AppointmentEntity> result = appointmentRepository.findAllByOrderByBookedAtDesc();

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    @Override
    public GetAppointmentsResponse getBarberAppointments(int id) {
        List<AppointmentEntity> result = appointmentRepository.findByBarber(Long.valueOf(id));

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    @Override
    public GetAppointmentsResponse getBarberAppointmentsByDate(int id, String date) {
        List<AppointmentEntity> result = appointmentRepository.findByBarberAndDate(Long.valueOf(id), date);

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    @Override
    public GetAppointmentsResponse getBarberAppointmentsBetweenDates(String start_date, String end_date, int barber_id){
        List<AppointmentEntity> result = appointmentRepository.findAppointmentsBetweenDates(start_date, end_date, barber_id);

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    @Override
    public GetAppointmentsResponse getBarberRecentAppointments(int barber_id) {
        List<AppointmentEntity> result = appointmentRepository.findBarberRecentAppointments(Long.valueOf(barber_id));

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    @Override
    public GetAppointmentsResponse getUserAppointments(int id) {
        List<AppointmentEntity> result = appointmentRepository.findAppointmentsByUser(Long.valueOf(id));

        final GetAppointmentsResponse response = new GetAppointmentsResponse();
        List<Appointment> appointments = convertList(result);

        response.setAppointments(appointments);

        return response;
    }

    private List<Appointment> convertList(List<AppointmentEntity> appointmentEntityList) {
        return appointmentEntityList
                .stream()
                .map(AppointmentConverter::convert)
                .toList();
    }
}
