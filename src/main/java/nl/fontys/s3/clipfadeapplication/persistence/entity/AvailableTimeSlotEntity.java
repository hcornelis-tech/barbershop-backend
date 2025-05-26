package nl.fontys.s3.clipfadeapplication.persistence.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AvailableTimeSlotEntity {
    private String date;
    private List<TimeSlotEntity> timeslots;
}
