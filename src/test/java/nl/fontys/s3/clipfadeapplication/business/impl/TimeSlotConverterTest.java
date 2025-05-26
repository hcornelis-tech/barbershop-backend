package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.TimeSlot;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeSlotEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeSlotConverterTest {
    @Test
    void convert() {
        TimeSlotEntity timeSlotEntity = TimeSlotEntity.builder()
                .time("10:00")
                .build();

        TimeSlot timeSlot = TimeSlotConverter.convert(timeSlotEntity);

        assertEquals(timeSlotEntity.getTime(), timeSlot.getTime());
    }
}
