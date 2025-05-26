package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "barbers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarberEntity {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "hire_date")
    private String hire_date;

    @Column(name = "is_admin")
    private boolean is_admin;

    //private List<AvailabilityEntity> availabilities;
}
