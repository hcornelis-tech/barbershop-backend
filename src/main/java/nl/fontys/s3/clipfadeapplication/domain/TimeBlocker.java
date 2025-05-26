package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;

@Data
@Builder
@AllArgsConstructor
public class TimeBlocker {
    private final int id;
    private final GetBarberResponse barber;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private boolean all_day;
}
