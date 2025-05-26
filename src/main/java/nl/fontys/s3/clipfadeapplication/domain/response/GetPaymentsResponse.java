package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.clipfadeapplication.domain.Payment;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentsResponse {
    private List<Payment> payments;
}
