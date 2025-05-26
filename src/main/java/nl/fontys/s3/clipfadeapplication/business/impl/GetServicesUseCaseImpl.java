package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetServicesUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.domain.response.GetServicesResponse;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class GetServicesUseCaseImpl implements GetServicesUseCase {
    private ServiceRepository serviceRepository;

    @Override
    public GetServicesResponse getServices() {
        List<ServiceEntity> result = serviceRepository.findAll();

        final GetServicesResponse getServicesResponse = new GetServicesResponse();
        List<Service> services = result
                .stream()
                .map(ServiceConverter::convert)
                .toList();

        getServicesResponse.setServices(services);

        return getServicesResponse;
    }
}
