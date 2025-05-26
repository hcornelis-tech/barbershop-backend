package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetScheduleUseCaseImpl implements GetScheduleUseCase {
    private ScheduleRepository scheduleRepository;

    @Override
    public Optional<Schedule> getSchedule() {
        return scheduleRepository.findById(Long.valueOf(1)).map(ScheduleConverter::convert);
    }
}
