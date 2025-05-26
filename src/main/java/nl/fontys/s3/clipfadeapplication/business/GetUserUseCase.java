package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;

public interface GetUserUseCase {
    GetUserResponse getUser(int id);
}
