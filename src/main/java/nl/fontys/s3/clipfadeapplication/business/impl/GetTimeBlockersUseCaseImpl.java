package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetTimeBlockersUseCase;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetTimeBlockersUseCaseImpl implements GetTimeBlockersUseCase {
    private TimeBlockerRepository timeBlockerRepository;

    @Override
    public GetTimeBlockersResponse getTimeBlockersByBarber(int id) {
        List<TimeBlockerEntity> result = timeBlockerRepository.findByBarberId((id));

        final GetTimeBlockersResponse getTimeBlockersResponse = new GetTimeBlockersResponse();
        List<GetTimeBlockerResponse> timeBlockers = result
                .stream()
                .map(TimeBlockerConverter::convert)
                .toList();

        getTimeBlockersResponse.setTimeblockers(timeBlockers);

        return getTimeBlockersResponse;
    }
}
