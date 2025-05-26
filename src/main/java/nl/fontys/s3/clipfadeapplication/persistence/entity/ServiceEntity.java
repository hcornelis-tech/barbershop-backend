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
@Table(name = "services")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @Column(name = "id")
    private int id;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "description")
    private String description;

    @Min(value = 1)
    @Column(name = "price")
    private double price;

    @Min(value = 1)
    @Column(name = "duration")
    private int duration;

    @OneToMany(mappedBy = "service")
    private List<AppointmentEntity> appointments;
}
