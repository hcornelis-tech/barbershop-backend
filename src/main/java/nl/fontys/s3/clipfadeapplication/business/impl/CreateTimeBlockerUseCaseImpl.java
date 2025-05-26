package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.DateTimeInThePastException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateTimeBlockerUseCaseImpl implements CreateTimeBlockerUseCase {
    private GenerateRandomId generateRandomId;
    private TimeBlockerRepository timeBlockerRepository;
    private BarberRepository barberRepository;

    @Override
    public CreateTimeBlockerResponse CreateTimeBlocker(CreateTimeBlockerRequest request) {
        LocalDate startDate = LocalDate.parse(request.getStart_date());
        LocalDate endDate = LocalDate.parse(request.getEnd_date());
        LocalTime startTime = LocalTime.parse(request.getStart_time());
        LocalTime endTime = LocalTime.parse(request.getEnd_time());

        int randomId;

        if (endDate.isBefore(startDate)) {
            throw new DateTimeInThePastException("End date cannot be before start date");
        }

        if (endTime.isBefore(startTime)) {
            throw new DateTimeInThePastException("End time cannot be before start time");
        }

        Optional<BarberEntity> barber = barberRepository.findById(Long.valueOf(request.getBarber_id()));
        if (barber.isEmpty()) {
            throw new InvalidBarberException("Barber not found");
        }

        do {
            randomId = generateRandomId.generate(10000, 90000);
        } while (timeBlockerRepository.existsById(Long.valueOf(randomId)));

        TimeBlockerEntity timeBlocker = TimeBlockerEntity.builder()
                .id(randomId)
                .barber(barber.get())
                .start_date(request.getStart_date())
                .end_date(request.getEnd_date())
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .all_day(request.isAll_day())
                .build();

        timeBlockerRepository.save(timeBlocker);

        return CreateTimeBlockerResponse.builder()
                .id(randomId)
                .build();
    }
}
