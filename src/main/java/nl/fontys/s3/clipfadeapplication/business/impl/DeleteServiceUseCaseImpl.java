package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.DeleteServiceUseCase;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {
    private ServiceRepository serviceRepository;

    @Override
    public void deleteService(int id) {
        serviceRepository.deleteById(Long.valueOf(id));
    }
}
