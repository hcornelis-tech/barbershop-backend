package nl.fontys.s3.clipfadeapplication.domain.response;

//import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.clipfadeapplication.domain.TimeBlocker;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBarberResponse {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String date_time;
    private String hire_date;
    private boolean is_admin;
//    private List<Appointment> appointments;
    private List<TimeBlocker> availabilities;
}
