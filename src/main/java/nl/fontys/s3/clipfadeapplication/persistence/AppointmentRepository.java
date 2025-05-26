package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.id = :id")
    Optional<AppointmentEntity> findAppointmentById(long id);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.barber.id = :id ORDER BY a.booked_at DESC")
    List<AppointmentEntity> findByBarber(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN false ELSE true END " +
            "FROM AppointmentEntity a " +
            "WHERE a.date = :date AND a.time = :time AND a.barber.id = :barberId AND a.status !='Cancelled'")
    boolean checkIfAvailable(@Param("date") String date, @Param("time") String time, @Param("barberId") Long barberId);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.barber.id = :id AND a.date = :date ORDER BY a.booked_at DESC")
    List<AppointmentEntity> findByBarberAndDate(@Param("id") Long id, @Param("date") String date);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments ORDER BY a.booked_at DESC")
    List<AppointmentEntity> findAllByOrderByBookedAtDesc();

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.date BETWEEN :start_date AND :end_date AND a.barber.id = :barberId ")
    List<AppointmentEntity> findAppointmentsBetweenDates(@Param("start_date") String start_date, @Param("end_date") String end_date, @Param("barberId") int barberId);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.barber.id = :barberId ORDER BY a.booked_at DESC LIMIT 5")
    List<AppointmentEntity> findBarberRecentAppointments(@Param("barberId") Long barberId);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.payments WHERE a.user.id = :id ORDER BY a.booked_at DESC")
    List<AppointmentEntity> findAppointmentsByUser(@Param("id") Long id);
}