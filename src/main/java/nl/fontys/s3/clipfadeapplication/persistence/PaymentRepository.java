package nl.fontys.s3.clipfadeapplication.persistence;

import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    @Query("SELECT p FROM PaymentEntity p WHERE p.date_time BETWEEN :start_date AND :end_date ")
    List<PaymentEntity> findPaymentsBetweenDates(@Param("start_date") String start_date, @Param("end_date") String end_date);

    @Query("SELECT p FROM PaymentEntity p WHERE p.date_time BETWEEN :start_date AND :end_date AND p.appointment.barber.id = :barberId ")
    List<PaymentEntity> findPaymentsBetweenDatesByBarber(@Param("start_date") String start_date, @Param("end_date") String end_date, @Param("barberId") int barberId);
}
