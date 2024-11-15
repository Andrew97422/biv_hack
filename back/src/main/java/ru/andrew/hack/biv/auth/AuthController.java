package ru.andrew.hack.biv.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andrew.hack.biv.auth.model.AuthenticationResponse;
import ru.andrew.hack.biv.auth.model.LoginRequest;
import ru.andrew.hack.biv.auth.model.RegisterRequest;
import ru.andrew.hack.biv.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController{

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @ModelAttribute RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @ModelAttribute LoginRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}