package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetBarberUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetBarberUseCaseImpl implements GetBarberUseCase {
    private BarberRepository barberRepository;

    @Override
    public GetBarberResponse getBarber(int id) {
        Optional<BarberEntity> optionalBarber =barberRepository.findByUserId(id);
        if (optionalBarber.isEmpty()) {
            throw new InvalidBarberException("Barber not found");
        }

        BarberEntity barber = optionalBarber.get();

        return BarberConverter.convert(barber);
    }
}
