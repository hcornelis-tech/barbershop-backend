package nl.fontys.s3.clipfadeapplication.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSlotEntity {
    private String time;
}
