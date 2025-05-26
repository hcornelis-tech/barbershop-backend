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
public class UpdateUserRequest {
    private int id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String email;

    @NotNull
    private String phone_number;
}
