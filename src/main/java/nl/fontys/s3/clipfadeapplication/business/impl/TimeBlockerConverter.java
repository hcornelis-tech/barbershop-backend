package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.TimeBlocker;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;

final class TimeBlockerConverter {
    private TimeBlockerConverter() {}

    public static GetTimeBlockerResponse convert(TimeBlockerEntity timeBlockerEntity) {
        return GetTimeBlockerResponse.builder()
                .id(timeBlockerEntity.getId())
                .barber_id(timeBlockerEntity.getBarber().getId())
                .start_date(timeBlockerEntity.getStart_date())
                .end_date(timeBlockerEntity.getEnd_date())
                .start_time(timeBlockerEntity.getStart_time())
                .end_time(timeBlockerEntity.getEnd_time())
                .all_day(timeBlockerEntity.isAll_day())
                .build();
    }
}
