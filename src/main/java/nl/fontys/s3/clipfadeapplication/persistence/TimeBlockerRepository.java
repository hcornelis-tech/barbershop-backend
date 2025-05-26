package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeBlockerRepository extends JpaRepository<TimeBlockerEntity, Long> {
    @Query("SELECT t FROM TimeBlockerEntity t WHERE :date BETWEEN t.start_date AND t.end_date AND t.barber.id = :barberId")
    TimeBlockerEntity checkIfBlockedBetweenDates(@Param("date") String date, @Param("barberId") Long barberId);

    List<TimeBlockerEntity> findByBarberId(int barberId);
}
