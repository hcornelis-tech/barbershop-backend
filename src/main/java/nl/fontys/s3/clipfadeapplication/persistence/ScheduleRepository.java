package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
