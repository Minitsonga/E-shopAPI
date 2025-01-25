package dev.minitsonga.E_shop.application.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.minitsonga.E_shop.application.dto.Users.AddressDTO;
import dev.minitsonga.E_shop.application.dto.Users.UserDTO;
import dev.minitsonga.E_shop.domain.User;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getPhone(),
                user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList()),
                new AddressDTO(user.getAddress(), user.getCity(), user.getZip()));

    }

}
