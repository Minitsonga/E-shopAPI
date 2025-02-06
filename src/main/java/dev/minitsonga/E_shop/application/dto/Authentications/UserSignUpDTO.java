package dev.minitsonga.E_shop.application.dto.Authentications;

import java.util.Set;

import dev.minitsonga.E_shop.domain.Role;
import dev.minitsonga.E_shop.domain.User;

public record UserSignUpDTO(
        String username,
        String email,
        String password) {

    public User convertToUser(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.setUsername(userSignUpDTO.username());
        user.setEmail(userSignUpDTO.email());
        user.setPassword(userSignUpDTO.password());
        // Rôle par défaut
        Role defaultRole = new Role();
        defaultRole.setRole("USER");
        user.setRoles(Set.of(defaultRole));
        return user;
    }
}
