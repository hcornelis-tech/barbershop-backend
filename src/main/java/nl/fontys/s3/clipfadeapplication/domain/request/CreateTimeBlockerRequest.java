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
public class CreateTimeBlockerRequest {
    @NotNull
    private int barber_id;

    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;

    @NotNull
    private boolean all_day;
}
