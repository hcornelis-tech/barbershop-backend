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
public class CreatePaymentRequest {
    @NotNull
    private double amount;

    @NotNull
    private String method;
}
