package nl.fontys.s3.clipfadeapplication.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdatePasswordUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdatePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PasswordController {
    private UpdatePasswordUseCase updatePasswordUseCase;

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable("id") int id, @RequestBody UpdatePasswordRequest request) {
        request.setId(id);
        updatePasswordUseCase.updatePassword(request);
        return ResponseEntity.noContent().build();
    }
}
