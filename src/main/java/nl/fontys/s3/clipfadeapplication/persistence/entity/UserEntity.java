package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id")
    private int id;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "first_name")
    private String first_name;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "last_name")
    private String last_name;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "phone_number")
    private String phone_number;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "date_time")
    private String date_time;

    @Column(name = "is_barber")
    private boolean is_barber;

    @OneToMany(mappedBy = "user")
    private List<AppointmentEntity> appointments;
}
