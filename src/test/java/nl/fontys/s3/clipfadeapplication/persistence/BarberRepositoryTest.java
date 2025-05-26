package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BarberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_shouldSaveBarberWithAllFields() {
        BarberEntity barber = getBarber();

        BarberEntity savedBarber = barberRepository.save(barber);
        assertNotNull(savedBarber.getId());

        savedBarber = entityManager.find(BarberEntity.class, savedBarber.getId());

        assertNotNull(savedBarber);
        assertEquals(barber.getUser().getEmail(), savedBarber.getUser().getEmail());
    }

    @Test
    void findByUserId_shouldReturnBarberSuccessfully() {
        BarberEntity barber = getBarber();
        BarberEntity savedBarber = barberRepository.save(barber);
        assertNotNull(savedBarber.getId());

        Optional<BarberEntity> barberOptional = barberRepository.findByUserId(savedBarber.getUser().getId());

        assertEquals(savedBarber, barberOptional.get());
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
                .appointments(Collections.emptyList())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return BarberEntity.builder()
                .user(savedUser)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();
    }
}