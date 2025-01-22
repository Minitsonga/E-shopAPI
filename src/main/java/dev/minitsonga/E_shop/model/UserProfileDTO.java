package dev.minitsonga.E_shop.model;

public record UserProfileDTO(
                String username,
                String gender,
                String firstName,
                String lastName,
                Integer age,
                AddressDTO addressDTO) {
}
