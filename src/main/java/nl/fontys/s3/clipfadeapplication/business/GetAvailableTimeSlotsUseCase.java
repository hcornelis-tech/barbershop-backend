package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetAvailableTimeSlotsResponse;

public interface GetAvailableTimeSlotsUseCase {
    GetAvailableTimeSlotsResponse availableTimeSlots(int barberId, int serviceDuration);
}