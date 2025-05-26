package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.domain.Barber;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateBarberResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarbersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/barbers")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BarberController {
    private GetBarbersUseCase getBarbersUseCase;
    private CreateBarberUseCase createBarberUseCase;
    private UpdateBarberUseCase updateBarberUseCase;
    private DeleteBarberUseCase deleteBarberUseCase;
    private GetBarberUseCase getBarberUseCase;

    @GetMapping
    public ResponseEntity<GetBarbersResponse> getBarbers() {
        GetBarbersResponse response = getBarbersUseCase.getBarbers();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetBarberResponse> getBarber(@PathVariable("id") int id) {
        GetBarberResponse response = getBarberUseCase.getBarber(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateBarberResponse> createBarber(@RequestBody @Valid CreateBarberRequest request) {
        CreateBarberResponse response = createBarberUseCase.createBarber(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateBarber(@PathVariable("id") int id, @RequestBody @Valid UpdateBarberRequest request) {
        request.setId(id);
        updateBarberUseCase.updateBarber(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBarber(@PathVariable("id") int id) {
        deleteBarberUseCase.deleteBarber(id);
        return ResponseEntity.noContent().build();
    }
}
