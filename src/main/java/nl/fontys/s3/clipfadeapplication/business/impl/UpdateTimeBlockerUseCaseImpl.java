package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidTimeBlockerException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateTimeBlockerUseCaseImpl implements UpdateTimeBlockerUseCase {
    private TimeBlockerRepository timeBlockerRepository;

    @Override
    public void updateTimeBlocker(UpdateTimeBlockerRequest request) {
        LocalDate startDate = LocalDate.parse(request.getStart_date());
        LocalDate endDate = LocalDate.parse(request.getEnd_date());
        LocalTime startTime = LocalTime.parse(request.getStart_time());
        LocalTime endTime = LocalTime.parse(request.getEnd_time());

        if (endDate.isBefore(startDate)) {
            throw new DateTimeInThePastException("End date cannot be before start date");
        }

        if (endTime.isBefore(startTime)) {
            throw new DateTimeInThePastException("End time cannot be before start time");
        }

        Optional<TimeBlockerEntity> timeBlockerOptional = timeBlockerRepository.findById(Long.valueOf(request.getId()));
        if (timeBlockerOptional.isEmpty()) {
            throw new InvalidTimeBlockerException("Time blocker with id" + request.getId() + " not found");
        }

        TimeBlockerEntity timeBlocker = timeBlockerOptional.get();

        timeBlocker.setStart_date(request.getStart_date());
        timeBlocker.setEnd_date(request.getEnd_date());
        timeBlocker.setStart_time(request.getStart_time());
        timeBlocker.setEnd_time(request.getEnd_time());
        timeBlocker.setAll_day(request.isAll_day());

        timeBlockerRepository.save(timeBlocker);
    }
}
