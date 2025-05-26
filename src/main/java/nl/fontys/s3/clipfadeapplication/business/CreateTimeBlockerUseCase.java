package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateTimeBlockerResponse;

public interface CreateTimeBlockerUseCase {
    CreateTimeBlockerResponse CreateTimeBlocker(CreateTimeBlockerRequest request);
}
