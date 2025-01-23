package dev.minitsonga.E_shop.domain.model;

public record UserProfileDTO(
        Long id,
        String username,
        String gender,
        String firstName,
        String lastName,
        Integer age,
        String email,
        AddressDTO addressDTO) {
}
