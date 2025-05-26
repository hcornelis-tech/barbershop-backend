package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Schedule;

import java.util.Optional;

public interface GetScheduleUseCase {
    Optional<Schedule> getSchedule();
}
