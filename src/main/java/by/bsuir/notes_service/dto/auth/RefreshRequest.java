package by.bsuir.notes_service.dto.auth;

import java.util.UUID;

public record RefreshRequest(
        UUID refreshToken
) {
}