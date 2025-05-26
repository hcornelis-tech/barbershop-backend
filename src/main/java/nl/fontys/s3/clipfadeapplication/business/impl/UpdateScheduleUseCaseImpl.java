package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidSlotIntervalException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateScheduleRequest;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateScheduleUseCaseImpl implements UpdateScheduleUseCase {
    private ScheduleRepository scheduleRepository;

    @Override
    public void updateSchedule(UpdateScheduleRequest request) {
        List<Integer> slotIntervals = new ArrayList<>();

        for (int i = 15; i <= 60; i += 5) {
            slotIntervals.add(i);
        }

        LocalTime startTime = LocalTime.parse(request.getStart_time());
        LocalTime endTime = LocalTime.parse(request.getEnd_time());

        if (endTime.isBefore(startTime)) {
            throw new DateTimeInThePastException("End time cannot be before start time");
        }

        Optional<ScheduleEntity> scheduleOptional = scheduleRepository.findById(Long.valueOf(request.getId()));
        if (scheduleOptional.isEmpty()) {
            throw new RuntimeException("Schedule not found!");
        }

        if (!slotIntervals.contains(request.getService_duration())) {
            throw new InvalidSlotIntervalException("Slot interval is not valid, should be between 15 and 60 and have an increment of 5min");
        }

        ScheduleEntity schedule = scheduleOptional.get();
        schedule.setStart_time(request.getStart_time());
        schedule.setEnd_time(request.getEnd_time());
        schedule.setService_duration(request.getService_duration());
        schedule.setMonday(request.isMonday());
        schedule.setTuesday(request.isTuesday());
        schedule.setWednesday(request.isWednesday());
        schedule.setThursday(request.isThursday());
        schedule.setFriday(request.isFriday());
        schedule.setSaturday(request.isSaturday());
        schedule.setSunday(request.isSunday());

        scheduleRepository.save(schedule);
    }
}
