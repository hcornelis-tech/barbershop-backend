package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetServicesResponse;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetServicesUseCaseImplTest {
    @Mock
    private ServiceRepository serviceRepository;

    @Test
    void getServices_shouldReturnAllServices() {
        // Arrange

        List<ServiceEntity> expectedServices = List.of(ServiceEntity.builder()
                .name("Full Cut")
                .description("Hello")
                .price(25.0)
                .build());

        when(serviceRepository.findAll()).thenReturn(expectedServices);

        GetServicesUseCaseImpl getServicesUseCase = new GetServicesUseCaseImpl(serviceRepository);

        //Act
        GetServicesResponse response = getServicesUseCase.getServices();

        //Assert
        assertEquals(expectedServices.size(), response.getServices().size());
    }

    @Test
    void getServices_shouldNotReturnServicesBecauseOfEmptyList() {
        // Arrange

        List<ServiceEntity> expectedServices = Collections.emptyList();

        when(serviceRepository.findAll()).thenReturn(expectedServices);

        GetServicesUseCaseImpl getServicesUseCase = new GetServicesUseCaseImpl(serviceRepository);

        //Act
        GetServicesResponse response = getServicesUseCase.getServices();

        //Assert
        assertThat(response.getServices().isEmpty());
    }

}
