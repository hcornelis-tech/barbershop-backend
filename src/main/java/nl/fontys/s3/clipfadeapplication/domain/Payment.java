package nl.fontys.s3.clipfadeapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Payment {
    private String id;
    private double amount;
    private String date_time;
    private String method;
    private String status;
}
