package nl.fontys.s3.clipfadeapplication.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBarberRequest {
    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String email;

    @NotNull
    private String phone_number;

    @NotNull
    private String password;

    @NotNull
    private String confirm_password;

    @NotNull
    private boolean is_barber;

    @NotNull
    private String date_time;

    @NotNull
    private String hire_date;

    @NotNull
    private boolean is_admin;
}
