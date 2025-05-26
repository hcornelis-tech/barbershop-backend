package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.business.UpdateScheduleUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Schedule;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateScheduleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleController {
    private GetScheduleUseCase getScheduleUseCase;
    private UpdateScheduleUseCase updateScheduleUseCase;

    @GetMapping
    public ResponseEntity<Schedule> getSchedule() {
        Optional<Schedule> schedule = getScheduleUseCase.getSchedule();
        if (schedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(schedule.get());
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateSchedule(@PathVariable("id") int id, @RequestBody @Valid UpdateScheduleRequest request) {
        request.setId(id);
        updateScheduleUseCase.updateSchedule(request);
        return ResponseEntity.noContent().build();
    }
}
