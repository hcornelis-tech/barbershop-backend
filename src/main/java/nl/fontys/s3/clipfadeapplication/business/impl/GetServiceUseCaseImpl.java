package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetServiceUseCase;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetServiceUseCaseImpl implements GetServiceUseCase {
    private ServiceRepository serviceRepository;

    @Override
    public Optional<nl.fontys.s3.clipfadeapplication.domain.Service> getService(int id) {
        return serviceRepository.findById(Long.valueOf(id)).map(ServiceConverter::convert);
    }
}
