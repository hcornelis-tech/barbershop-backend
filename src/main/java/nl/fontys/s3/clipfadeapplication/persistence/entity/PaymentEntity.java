package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    @Min(1)
    @Column(name = "amount")
    private double amount;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "date_time")
    private String date_time;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "method")
    private String method;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "status")
    private String status;
}
