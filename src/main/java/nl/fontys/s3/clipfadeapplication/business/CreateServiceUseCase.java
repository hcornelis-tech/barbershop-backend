package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateServiceRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateServiceResponse;

public interface CreateServiceUseCase {
    CreateServiceResponse createService(CreateServiceRequest request);
}
