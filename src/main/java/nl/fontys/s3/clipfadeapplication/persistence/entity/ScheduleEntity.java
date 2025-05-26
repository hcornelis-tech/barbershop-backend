package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "schedule")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {
    @Id
    @Column(name = "id")
    private int id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "start_time")
    private String start_time;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "end_time")
    private String end_time;

    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "service_duration")
    private int service_duration;

    @Column(name = "monday")
    private boolean monday;

    @Column(name = "tuesday")
    private boolean tuesday;

    @Column(name = "wednesday")
    private boolean wednesday;

    @Column(name = "thursday")
    private boolean thursday;

    @Column(name = "friday")
    private boolean friday;

    @Column(name = "saturday")
    private boolean saturday;

    @Column(name = "sunday")
    private boolean sunday;
}
