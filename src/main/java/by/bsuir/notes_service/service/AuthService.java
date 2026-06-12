package by.bsuir.notes_service.service;

import by.bsuir.notes_service.dto.auth.RegisterRequest;
import by.bsuir.notes_service.dto.auth.RegisterResponse;
import by.bsuir.notes_service.entity.User;
import by.bsuir.notes_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException(
                    "User with this email already exists"
            );
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(
                        passwordService.hash(
                                request.password()
                        )
                )
                .createdAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                user.getEmail()
        );
    }
}