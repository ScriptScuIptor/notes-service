package by.bsuir.notes_service.service;

import by.bsuir.notes_service.dto.auth.AuthResponse;
import by.bsuir.notes_service.dto.auth.LoginRequest;
import by.bsuir.notes_service.entity.RefreshToken;
import by.bsuir.notes_service.entity.User;
import by.bsuir.notes_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken =
                jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService.create(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public AuthResponse refresh(UUID refreshTokenValue) {

        RefreshToken refreshToken =
                refreshTokenService.validate(refreshTokenValue);

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateAccessToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public void logout(UUID refreshTokenValue) {

        RefreshToken refreshToken =
                refreshTokenService.validate(refreshTokenValue);

        refreshTokenService.revoke(refreshToken);
    }
}