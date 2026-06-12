package by.bsuir.notes_service.controller;

import by.bsuir.notes_service.dto.auth.RegisterRequest;
import by.bsuir.notes_service.dto.auth.RegisterResponse;
import by.bsuir.notes_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody
            @Valid
            RegisterRequest request
    ) {
        return authService.register(request);
    }
}