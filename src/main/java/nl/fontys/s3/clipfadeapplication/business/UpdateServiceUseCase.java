package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateServiceRequest;

public interface UpdateServiceUseCase {
    void updateService(UpdateServiceRequest request);
}
