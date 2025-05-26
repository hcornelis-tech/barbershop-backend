package nl.fontys.s3.clipfadeapplication.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
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
class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_shouldSaveUserWithAllFields() {
        UserEntity user = UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build();

        UserEntity savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        savedUser = entityManager.find(UserEntity.class, savedUser.getId());
        UserEntity expectedUser = UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build();

        assertEquals(expectedUser, savedUser);
    }
}
