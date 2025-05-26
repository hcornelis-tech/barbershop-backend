package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TimeSlot {
    private String time;
}
