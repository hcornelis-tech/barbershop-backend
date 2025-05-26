package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidServiceException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateServiceRequest;
import nl.fontys.s3.clipfadeapplication.persistence.ServiceRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateServiceUseCaseImplTest {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private UpdateServiceUseCaseImpl updateServiceUseCase;

    @Test
    void updateService_shouldUpdateService() {
        //Arrange
        UpdateServiceRequest request = getUpdateServiceRequest();
        ServiceEntity service = ServiceEntity.builder()
                .id(1)
                .name("Full Cut")
                .description("Hello")
                .duration(45)
                .price(25.0)
                .build();

        when(serviceRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(service));

        //Act
        updateServiceUseCase.updateService(request);

        //Assert
        assertEquals(request.getDuration(), service.getDuration());
    }

    @Test
    void updateService_shouldThrowInvalidServiceException_whenServiceDoesNotExist() {
        // Arrange
        UpdateServiceRequest request = getUpdateServiceRequest();
        when(serviceRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidServiceException.class, () -> updateServiceUseCase.updateService(request));
    }

    private UpdateServiceRequest getUpdateServiceRequest() {
        return UpdateServiceRequest.builder()
                .id(1)
                .name("Full Cut")
                .description("Hello")
                .duration(30)
                .price(25.0)
                .build();
    }
}
