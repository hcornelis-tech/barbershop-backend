package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateServiceUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.IdAlreadyExists;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateServiceRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateServiceResponse;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CreateServiceUseCaseImpl implements CreateServiceUseCase {
    private GenerateRandomId generateRandomId;
    private ServiceRepository serviceRepository;

    @Override
    public CreateServiceResponse createService(CreateServiceRequest request) {
        int randomId = generateUniqueRandomId();

        ServiceEntity services = saveService(request, randomId);

         return CreateServiceResponse.builder()
                 .id(services.getId())
                 .build();
    }

    private ServiceEntity saveService(CreateServiceRequest request, int randomId) {
        ServiceEntity newServices = ServiceEntity.builder()
                .id(randomId)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .appointments(Collections.emptyList())
                .build();

        return serviceRepository.save(newServices);
    }

    private int generateUniqueRandomId() {
        int randomId;

        do {
            randomId = generateRandomId.generate(10000, 90000);
        } while (serviceRepository.existsById(Long.valueOf(randomId)));

        return randomId;
    }
}
