package by.bsuir.notes_service.controller;

import by.bsuir.notes_service.dto.auth.AuthResponse;
import by.bsuir.notes_service.dto.auth.LoginRequest;
import by.bsuir.notes_service.dto.auth.RefreshRequest;
import by.bsuir.notes_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestBody RefreshRequest request
    ) {

        return ResponseEntity.ok(
                authService.refresh(
                        request.refreshToken()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody RefreshRequest request
    ) {

        authService.logout(
                request.refreshToken()
        );

        return ResponseEntity.ok(
                "Logged out successfully"
        );
    }
}