package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentResponse {
    private String id;
}
