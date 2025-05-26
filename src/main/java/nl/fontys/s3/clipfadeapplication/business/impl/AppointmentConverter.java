package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;

final class AppointmentConverter {
    private AppointmentConverter() {}

    public static Appointment convert(AppointmentEntity appointment) {
        return Appointment.builder()
                .id(appointment.getId())
                .customer(UserConverter.convert(appointment.getUser()))
                .barber(BarberConverter.convert(appointment.getBarber()))
                .service(ServiceConverter.convert(appointment.getService()))
                .date(appointment.getDate())
                .time(appointment.getTime())
                .status(appointment.getStatus())
                .total_price(appointment.getTotal_price())
                .booked_at(appointment.getBooked_at())
                .payment_method(appointment.getPayment_method())
                .payments(appointment.getPayments().stream().map(PaymentConverter::convert).toList())
                .booked_online(appointment.isBooked_online())
                .build();
    }
}
