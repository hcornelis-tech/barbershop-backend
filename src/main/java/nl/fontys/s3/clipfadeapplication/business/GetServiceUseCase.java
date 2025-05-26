package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.Service;

import java.util.Optional;

public interface GetServiceUseCase {
    Optional<Service> getService(int id);
}
