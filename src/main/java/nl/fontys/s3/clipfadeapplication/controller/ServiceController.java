package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateServiceRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateServiceRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateServiceResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetServicesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/services")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController {
    private GetServicesUseCase getServicesUseCase;
    private CreateServiceUseCase createServiceUseCase;
    private UpdateServiceUseCase updateServiceUseCase;
    private DeleteServiceUseCase deleteServiceUseCase;
    private GetServiceUseCase getServiceResponse;

    @GetMapping
    public ResponseEntity<GetServicesResponse> getServices() {
        GetServicesResponse response = getServicesUseCase.getServices();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Service> getService(@PathVariable("id") int id) {
        Optional<Service> serviceOptional = getServiceResponse.getService(id);
        if (serviceOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(serviceOptional.get());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateServiceResponse> createServices(@RequestBody @Valid CreateServiceRequest request) {
        CreateServiceResponse response = createServiceUseCase.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateServices(@PathVariable("id") int id, @RequestBody @Valid UpdateServiceRequest request) {
        request.setId(id);
        updateServiceUseCase.updateService(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteServices(@PathVariable("id") int id) {
        deleteServiceUseCase.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
