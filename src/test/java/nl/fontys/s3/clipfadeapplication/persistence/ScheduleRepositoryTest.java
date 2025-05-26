package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ScheduleRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    void save_shouldSaveScheduleWithAllFields() {
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("16:00")
                .service_duration(30)
                .build();

        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);
        assertNotNull(savedSchedule.getId());

        savedSchedule = entityManager.find(ScheduleEntity.class, savedSchedule.getId());

        ScheduleEntity expectedSchedule = ScheduleEntity.builder()
                .id(1)
                .start_time("10:00")
                .end_time("16:00")
                .service_duration(30)
                .build();

        assertEquals(expectedSchedule, savedSchedule);
    }
}
