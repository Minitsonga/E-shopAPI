package dev.minitsonga.E_shop.model;

import java.util.List;

public record UserDTO(
        String username,
        String firstName,
        String lastName,
        Integer age,
        String email,
        String phone,
        List<String> roles,
        AddressDTO addressDTO) {

   

}
