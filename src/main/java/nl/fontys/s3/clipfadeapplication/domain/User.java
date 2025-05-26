package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class User {
    private final int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String password;
    private final String date_time;
    private final boolean is_barber;
    private List<Appointment> appointments;
}
