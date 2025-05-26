package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ServiceRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    ServiceRepository serviceRepository;

    @Test
    void save_shouldSaveServiceWithAllFields() {
        ServiceEntity service = ServiceEntity.builder()
                .id(2)
                .name("test cut")
                .description("test description")
                .price(25.00)
                .appointments(Collections.emptyList())
                .build();

        ServiceEntity savedService = serviceRepository.save(service);
        assertNotNull(savedService.getId());

        savedService = entityManager.find(ServiceEntity.class, savedService.getId());

        ServiceEntity expectedService = ServiceEntity.builder()
                .id(2)
                .name("test cut")
                .description("test description")
                .price(25.00)
                .appointments(Collections.emptyList())
                .build();

        assertEquals(expectedService, savedService);
    }
}
