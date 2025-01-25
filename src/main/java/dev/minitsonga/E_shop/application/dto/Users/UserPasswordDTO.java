package dev.minitsonga.E_shop.application.dto.Users;

public record UserPasswordDTO(
        String username,
        String oldPassword,
        String newPassword) {
}
