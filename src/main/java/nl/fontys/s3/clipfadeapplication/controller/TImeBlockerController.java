package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.DeleteTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.business.GetTimeBlockersUseCase;
import nl.fontys.s3.clipfadeapplication.business.UpdateTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateTimeBlockerRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time-blocker")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TImeBlockerController {
    private CreateTimeBlockerUseCase createTimeBlockerUseCase;
    private GetTimeBlockersUseCase getTimeBlockersUseCase;
    private DeleteTimeBlockerUseCase deleteTimeBlockerUseCase;
    private UpdateTimeBlockerUseCase updateTimeBlockerUseCase;

    @GetMapping("{id}")
    public ResponseEntity<GetTimeBlockersResponse> getTimeBlockersByBarber(@PathVariable("id") int id) {
        GetTimeBlockersResponse response = getTimeBlockersUseCase.getTimeBlockersByBarber(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CreateTimeBlockerResponse> createTimeBlocker(@RequestBody @Valid CreateTimeBlockerRequest request) {
        CreateTimeBlockerResponse response = createTimeBlockerUseCase.CreateTimeBlocker(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTimeBlocker(@PathVariable("id") int id, @RequestBody @Valid UpdateTimeBlockerRequest request) {
        request.setId(id);
        updateTimeBlockerUseCase.updateTimeBlocker(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTimeBlocker(@PathVariable("id") int id) {
        deleteTimeBlockerUseCase.deleteTimeBlocker(id);
        return ResponseEntity.noContent().build();
    }
}
