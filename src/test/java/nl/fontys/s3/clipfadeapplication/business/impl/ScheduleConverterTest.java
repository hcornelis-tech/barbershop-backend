package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleConverterTest {
    @Test
    void convert() {
        //Arrange
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("17:00")
                .service_duration(30)
                .build();

        Schedule schedule = ScheduleConverter.convert(scheduleEntity);

        //Assert
        assertEquals(scheduleEntity.getId(), schedule.getId());
        assertEquals(scheduleEntity.getStart_time(), schedule.getStart_time());
        assertEquals(scheduleEntity.getEnd_time(), schedule.getEnd_time());
        assertEquals(scheduleEntity.getService_duration(), schedule.getService_duration());
    }
}
