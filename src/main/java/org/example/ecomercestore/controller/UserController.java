package org.example.ecomercestore.controller;

import jakarta.validation.Valid;
import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserResponseDTO getByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);
    }


    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO dto) {
        return service.saveUser(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid@RequestBody UserRequestDTO dto) {
        UserResponseDTO updatedUser = service.updateUser(id,dto);
        return ResponseEntity.ok(updatedUser);
    }
}
