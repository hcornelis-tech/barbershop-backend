package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetUserResponse {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String date_time;
    private boolean is_barber;
}
