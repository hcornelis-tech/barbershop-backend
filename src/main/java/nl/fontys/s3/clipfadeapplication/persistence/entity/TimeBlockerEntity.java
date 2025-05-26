package nl.fontys.s3.clipfadeapplication.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timeblocker")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeBlockerEntity {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private BarberEntity barber;

    @Column(name = "start_date")
    private String start_date;

    @Column(name = "end_date")
    private String end_date;

    @Column(name = "start_time")
    private String start_time;

    @Column(name = "end_time")
    private String end_time;

    @Column(name = "all_day")
    private boolean all_day;
}
