package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
