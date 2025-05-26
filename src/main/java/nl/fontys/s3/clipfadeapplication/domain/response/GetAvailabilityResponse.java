package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAvailabilityResponse {
    private int id;
    private GetBarberResponse barber;
    private String date;
    private String start_time;
    private String end_time;
}
