package dev.minitsonga.E_shop.model;

public record UserPasswordDTO(
        String username,
        String oldPassword,
        String newPassword) {
}
