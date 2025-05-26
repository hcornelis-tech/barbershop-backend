package nl.fontys.s3.clipfadeapplication.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.clipfadeapplication.domain.Service;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetServicesResponse {
    private List<Service> services;
}
