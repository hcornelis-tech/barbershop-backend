package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteServiceUseCaseImplTest {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private DeleteServiceUseCaseImpl deleteServiceUseCase;

    @Test
    void deleteService() {
        int serviceId = 1;

        deleteServiceUseCase.deleteService(serviceId);

        verify(serviceRepository).deleteById(Long.valueOf(serviceId));
    }
}
