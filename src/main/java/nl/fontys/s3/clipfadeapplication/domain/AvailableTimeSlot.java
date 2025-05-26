package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AvailableTimeSlot {
    private String date;
    private List<TimeSlot> timeslots;
}
