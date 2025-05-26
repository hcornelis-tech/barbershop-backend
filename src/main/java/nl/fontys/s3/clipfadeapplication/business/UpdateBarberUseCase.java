package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Barber;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;

public interface UpdateBarberUseCase {
    void updateBarber(UpdateBarberRequest request);
}
