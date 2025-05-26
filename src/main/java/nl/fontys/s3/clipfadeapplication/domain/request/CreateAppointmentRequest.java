package nl.fontys.s3.clipfadeapplication.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    @NotNull
    private int user_id;

    @NotNull
    private int barber_id;

    @NotNull
    private int service_id;

    @NotNull
    private String date;

    @NotNull
    private String time;

    @NotNull
    private String status;

    @NotNull
    private double total_price;

    @NotNull
    private String booked_at;

    @NotNull
    private String payment;

    @NotNull
    private boolean booked_online;
}
