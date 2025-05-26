package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockersResponse;

public interface GetTimeBlockersUseCase {
    GetTimeBlockersResponse getTimeBlockersByBarber(int id);
}
