package dev.minitsonga.E_shop.application.dto.Authentications;

public record AuthenticationRequestDTO(
        String usernameOrEmail,
        String password) {
}
