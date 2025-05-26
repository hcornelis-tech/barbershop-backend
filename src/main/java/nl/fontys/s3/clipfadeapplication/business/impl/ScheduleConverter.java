package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;

final class ScheduleConverter {
    private ScheduleConverter() {}

    public static Schedule convert(ScheduleEntity scheduleEntity) {
        return Schedule.builder()
                .id(scheduleEntity.getId())
                .start_time(scheduleEntity.getStart_time())
                .end_time(scheduleEntity.getEnd_time())
                .service_duration(scheduleEntity.getService_duration())
                .monday(scheduleEntity.isMonday())
                .tuesday(scheduleEntity.isTuesday())
                .wednesday(scheduleEntity.isWednesday())
                .thursday(scheduleEntity.isThursday())
                .friday(scheduleEntity.isFriday())
                .saturday(scheduleEntity.isSaturday())
                .sunday(scheduleEntity.isSunday())
                .build();
    }
}
