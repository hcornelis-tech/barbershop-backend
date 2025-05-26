package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Barber;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;

import java.util.Optional;

public interface GetBarberUseCase {
    GetBarberResponse getBarber(int id);
}
