package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdatePasswordRequest;

public interface UpdatePasswordUseCase {
    void updatePassword(UpdatePasswordRequest request);
}
