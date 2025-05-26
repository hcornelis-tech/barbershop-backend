package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Appointment {
    private final int id;
    private final GetUserResponse customer;
    private final GetBarberResponse barber;
    private Service service;
    private String date;
    private String time;
    private String status;
    private double total_price;
    private String booked_at;
    private String payment_method;
    private List<Payment> payments;
    private boolean booked_online;
}
