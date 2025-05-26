package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Service {
    private final int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private List<Appointment> appointments;
}
