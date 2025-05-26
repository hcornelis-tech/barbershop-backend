package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateServiceUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidServiceException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateServiceRequest;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {
    private ServiceRepository serviceRepository;

    @Override
    public void updateService(UpdateServiceRequest request) {
        Optional<ServiceEntity> serviceOptional = serviceRepository.findById(Long.valueOf(request.getId()));
        if (serviceOptional.isEmpty()) {
            throw new InvalidServiceException("Service with id " + request.getId() + " not found");
        }

        ServiceEntity service = serviceOptional.get();

        service.setPrice(request.getPrice());
        service.setDescription(request.getDescription());
        service.setName(request.getName());
        service.setDuration(request.getDuration());

        serviceRepository.save(service);
    }
}