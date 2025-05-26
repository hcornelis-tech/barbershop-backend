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
public class CreateServiceRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private double price;

    @NotNull
    private int duration;
}
