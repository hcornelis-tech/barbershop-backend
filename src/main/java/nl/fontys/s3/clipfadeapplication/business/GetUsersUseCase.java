package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetUsersResponse;

public interface GetUsersUseCase {
    GetUsersResponse getUsers();
    GetUsersResponse getUsersByBarber(int id);
}
