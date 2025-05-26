package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.AvailableTimeSlot;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AvailableTimeSlotEntity;

final class AvailableTimeSlotConverter {
    private AvailableTimeSlotConverter() {}

    public static AvailableTimeSlot convert(AvailableTimeSlotEntity availableTimeSlotEntity) {
        return AvailableTimeSlot.builder()
                .date(availableTimeSlotEntity.getDate())
                .timeslots(availableTimeSlotEntity.getTimeslots().stream().map(TimeSlotConverter::convert).toList())
                .build();
    }
}
