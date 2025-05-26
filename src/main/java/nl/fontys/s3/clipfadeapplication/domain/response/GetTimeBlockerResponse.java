package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetTimeBlockerResponse {
    private int id;
    private int barber_id;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private boolean all_day;
}
