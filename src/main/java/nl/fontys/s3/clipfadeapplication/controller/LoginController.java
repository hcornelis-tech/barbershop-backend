package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.LoginUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.LoginRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
