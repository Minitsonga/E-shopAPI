package dev.minitsonga.E_shop.application.dto;

public record UserPasswordDTO(
        String username,
        String oldPassword,
        String newPassword) {
}
