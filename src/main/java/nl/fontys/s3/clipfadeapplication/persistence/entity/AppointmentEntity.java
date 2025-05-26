package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "appointments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private BarberEntity barber;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "date")
    private String date;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "time")
    private String time;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "status")
    private String status;

    @Min(value = 1)
    @Column(name = "total_cost")
    private double total_price;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "booked_at")
    private String booked_at;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "payment")
    private String payment_method;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEntity> payments;

    @Column(name = "booked_online")
    private boolean booked_online;
}
