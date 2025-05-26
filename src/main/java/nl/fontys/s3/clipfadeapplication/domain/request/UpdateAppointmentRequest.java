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
public class UpdateAppointmentRequest {
    @NotNull
    private int id;

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
    private boolean by_user;
}
