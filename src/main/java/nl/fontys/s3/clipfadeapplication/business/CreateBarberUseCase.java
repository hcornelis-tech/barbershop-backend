package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateBarberResponse;

public interface CreateBarberUseCase {
    CreateBarberResponse createBarber(CreateBarberRequest request);
}
