package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetBarbersUseCase;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarbersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetBarbersUseCaseImpl implements GetBarbersUseCase {
    private BarberRepository barberRepository;

    public GetBarbersResponse getBarbers() {
        List<BarberEntity> results = barberRepository.findAll();

        final GetBarbersResponse getBarbersResponse = new GetBarbersResponse();
        List<GetBarberResponse> barbers = results
                .stream()
                .map(BarberConverter::convert)
                .toList();

        getBarbersResponse.setBarbers(barbers);

        return getBarbersResponse;
    }
}
