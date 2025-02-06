package dev.minitsonga.E_shop.application.dto.Authentications;

import dev.minitsonga.E_shop.domain.RefreshToken;

public record AuthenticationResponseDTO(
        String accessToken,
        String refreshToken) {
}
