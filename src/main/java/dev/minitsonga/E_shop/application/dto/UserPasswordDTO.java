package dev.minitsonga.E_shop.domain.model;

public record UserPasswordDTO(
        String username,
        String oldPassword,
        String newPassword) {
}
