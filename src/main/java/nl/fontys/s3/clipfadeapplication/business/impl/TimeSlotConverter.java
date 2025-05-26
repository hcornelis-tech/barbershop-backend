package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.TimeSlot;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeSlotEntity;

final class TimeSlotConverter {
    private TimeSlotConverter() {}

    public static TimeSlot convert(TimeSlotEntity timeSlot) {
        return TimeSlot.builder()
                .time(timeSlot.getTime())
                .build();
    }
}
