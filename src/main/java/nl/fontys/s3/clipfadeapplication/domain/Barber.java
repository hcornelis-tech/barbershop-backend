package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Barber {
    private int id;
    private User user;
    private String hire_date;
    private boolean is_admin;
    private List<TimeBlocker> availabilities;
}
