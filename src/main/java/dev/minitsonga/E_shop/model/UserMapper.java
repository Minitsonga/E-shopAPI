package dev.minitsonga.E_shop.model;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Function<Record, User> {
    @Override
    public User apply(Record r) {

        User user = new User();
        UserSignUpDTO userSignUpDTO = (UserSignUpDTO) r;
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
