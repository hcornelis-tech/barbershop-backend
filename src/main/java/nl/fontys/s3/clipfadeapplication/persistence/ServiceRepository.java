package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}
