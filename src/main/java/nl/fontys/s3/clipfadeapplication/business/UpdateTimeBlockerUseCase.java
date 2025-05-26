package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateTimeBlockerRequest;

public interface UpdateTimeBlockerUseCase {
    void updateTimeBlocker(UpdateTimeBlockerRequest request);
}
