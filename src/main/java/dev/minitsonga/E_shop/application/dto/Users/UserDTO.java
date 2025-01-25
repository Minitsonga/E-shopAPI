package dev.minitsonga.E_shop.application.dto.Users;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        Integer age,
        String email,
        String phone,
        List<String> roles,
        AddressDTO addressDTO) {

   

}
