package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceConverterTest {
    @Test
    void convert() {
        //Arrange
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(1)
                .name("Test Service")
                .description("Test Description")
                .price(25)
                .duration(30)
                .appointments(Collections.emptyList())
                .build();

        Service service = ServiceConverter.convert(serviceEntity);

        //Assert
        assertEquals(serviceEntity.getId(), service.getId());
        assertEquals(serviceEntity.getName(), service.getName());
        assertEquals(serviceEntity.getDescription(), service.getDescription());
        assertEquals(serviceEntity.getPrice(), service.getPrice());
        assertEquals(serviceEntity.getDuration(), service.getDuration());
    }
}
