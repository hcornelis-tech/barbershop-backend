package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetServiceUseCaseImplTest {
    @Mock
    private ServiceRepository serviceRepository;

    @Test
    void getService_ShouldReturnService() {
        //Arrange
        ServiceEntity service = ServiceEntity.builder()
                .id(1)
                .name("Full Cut")
                .description("Hello")
                .price(25.0)
                .build();

        when(serviceRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(service));

        GetServiceUseCaseImpl getServiceUseCase = new GetServiceUseCaseImpl(serviceRepository);

        //Act
        Optional<Service> response = getServiceUseCase.getService(1);

        //Assert
        assertEquals(service.getId(), response.get().getId());
        assertEquals(service.getName(), response.get().getName());
        assertEquals(service.getPrice(), response.get().getPrice());
    }
}
