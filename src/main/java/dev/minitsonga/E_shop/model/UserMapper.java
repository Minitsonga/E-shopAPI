package dev.minitsonga.E_shop.model;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Function<Record, User> {

 
    // private User mapFromUserSignUpDTO(UserSignUpDTO userSignUpDTO) {
    // User user = new User();
    // user.setUsername(userSignUpDTO.username());
    // user.setEmail(userSignUpDTO.email());
    // user.setPassword(userSignUpDTO.password());
    // // Rôle par défaut
    // Role defaultRole = new Role();
    // defaultRole.setRole("USER");
    // user.setRoles(Set.of(defaultRole));
    // return user;
    // }

    // public User mapFromUserProfileDTO(UserProfileDTO userProfileDTO) {
    // User user = new User();
    // user.setUsername(userSignUpDTO.username());
    // user.setEmail(userSignUpDTO.email());
    // user.setPassword(userSignUpDTO.password());
    // // Rôle par défaut
    // Role defaultRole = new Role();
    // defaultRole.setRole("USER");
    // user.setRoles(Set.of(defaultRole));
    // return user;
    // }

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

        // switch (r) {
        // case UserSignUpDTO userSignUpDTO:

        // break;
        // case UserDTO userDTO:
        // return mapFromUserDTO(userDTO);
        // break;
        // case UserProfileDTO userProfileDTO:
        // return mapFromUserProfileDTO(userProfileDTO);
        // break;

        // default:
        // throw new UnsupportedOperationException("Unsupported Record type: " +
        // r.getClass().getName());
        // break;
        // }
    }
}
