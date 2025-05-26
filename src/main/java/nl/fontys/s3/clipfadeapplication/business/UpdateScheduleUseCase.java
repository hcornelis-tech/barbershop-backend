package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateScheduleRequest;

public interface UpdateScheduleUseCase {
    void updateSchedule(UpdateScheduleRequest request);
}