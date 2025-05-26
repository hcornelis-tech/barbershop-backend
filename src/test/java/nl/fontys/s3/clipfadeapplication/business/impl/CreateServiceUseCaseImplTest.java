package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateServiceRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateServiceResponse;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateServiceUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private GenerateRandomId generateRandomId;

    @InjectMocks
    private CreateServiceUseCaseImpl createServiceUseCase;

    @Test
    void createService_shouldReturnServiceCreatedSuccessfully() {
        // Arrange
        CreateServiceRequest request = createServiceRequest();

        ServiceEntity expectedService = ServiceEntity.builder()
                .name("Full Cut")
                .description("Hello")
                .price(25.0)
                .build();

        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(expectedService);

        when(generateRandomId.generate(anyInt(), anyInt())).thenReturn(12345);

        // Act
        CreateServiceResponse actualService = createServiceUseCase.createService(request);

        // Assertions
        assertEquals(expectedService.getId(), actualService.getId());
    }

    private CreateServiceRequest createServiceRequest() {
        return CreateServiceRequest.builder()
                .name("Full Cut")
                .description("Hello")
                .duration(30)
                .price(25.0)
                .build();
    }
}
