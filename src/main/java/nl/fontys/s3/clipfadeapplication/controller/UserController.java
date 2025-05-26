package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateUserResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUsersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private GetUsersUseCase getUsersUseCase;
    private CreateUserUseCase createUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private GetUserUseCase getUserUseCase;

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() {
        GetUsersResponse response = getUsersUseCase.getUsers();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("id") int id) {
        GetUserResponse response = getUserUseCase.getUser(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/barber/{id}")
    public ResponseEntity<GetUsersResponse> getUsersByBarber(@PathVariable("id") int id) {
        GetUsersResponse response = getUsersUseCase.getUsersByBarber(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @RequestBody @Valid UpdateUserRequest request) {
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
