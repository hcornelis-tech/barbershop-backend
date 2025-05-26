package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsById(int id);
    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.appointments a WHERE a.barber.id = :barberId")
    List<UserEntity> findByBarber(@Param("barberId") Long barberId);
}
