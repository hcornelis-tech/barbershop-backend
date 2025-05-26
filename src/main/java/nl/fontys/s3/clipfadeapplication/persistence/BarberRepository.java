package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarberRepository extends JpaRepository<BarberEntity, Long> {
    Optional<BarberEntity> findByUserId(int id);
}
