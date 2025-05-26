package nl.fontys.s3.clipfadeapplication.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetAvailableTimeSlotsUseCase;
import nl.fontys.s3.clipfadeapplication.domain.response.GetAvailableTimeSlotsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/availabilities")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AvailabilityController {
    private GetAvailableTimeSlotsUseCase getAvailableTimeSlotsUseCase;

    @GetMapping()
    public ResponseEntity<GetAvailableTimeSlotsResponse> GetAvailability(@RequestParam("barber") int barberId, @RequestParam("service") int serviceDuration) {
        GetAvailableTimeSlotsResponse response = getAvailableTimeSlotsUseCase.availableTimeSlots(barberId, serviceDuration);
        return ResponseEntity.ok().body(response);
    }
}
