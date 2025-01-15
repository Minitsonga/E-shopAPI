package dev.minitsonga.E_shop.model;

public record UserDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        String phone,
        AddressDTO addressDTO) {

}
