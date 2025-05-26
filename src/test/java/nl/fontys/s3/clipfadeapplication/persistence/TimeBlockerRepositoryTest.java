package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TimeBlockerRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TimeBlockerRepository timeBlockerRepository;

    @Test
    void save_shouldSaveTimeBlockerWithAllFields() {
        BarberEntity barber = getBarber();

        TimeBlockerEntity timeBlocker = TimeBlockerEntity.builder()
                .barber(barber)
                .start_date("2024-11-04")
                .end_date("2024-11-04")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();

        TimeBlockerEntity savedTimeBlocker = timeBlockerRepository.save(timeBlocker);
        assertNotNull(savedTimeBlocker);

        savedTimeBlocker = entityManager.find(TimeBlockerEntity.class, savedTimeBlocker.getId());

        TimeBlockerEntity expectedTimeBlocker = TimeBlockerEntity.builder()
                .id(savedTimeBlocker.getId())
                .barber(barber)
                .start_date("2024-11-04")
                .end_date("2024-11-04")
                .start_time("10:00")
                .end_time("11:00")
                .all_day(false)
                .build();

        assertEquals(expectedTimeBlocker, savedTimeBlocker);
    }

    private BarberEntity getBarber() {
        UserEntity user = UserEntity.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(true)
                .build();

        return BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }
}
