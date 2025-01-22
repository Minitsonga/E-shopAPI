package dev.minitsonga.E_shop.service;

import dev.minitsonga.E_shop.model.AddressDTO;
import dev.minitsonga.E_shop.model.Role;
import dev.minitsonga.E_shop.model.User;
import dev.minitsonga.E_shop.model.UserDTO;
import dev.minitsonga.E_shop.model.UserDTOMapper;
import dev.minitsonga.E_shop.model.UserMapper;
import dev.minitsonga.E_shop.model.UserProfileDTO;
import dev.minitsonga.E_shop.model.UserSignUpDTO;
import dev.minitsonga.E_shop.repo.RoleRepo;
import dev.minitsonga.E_shop.repo.UserRepo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserDTOMapper userDTOMapper;
    private final UserMapper userMapper;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserDTOMapper userDTOMapper, UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.userDTOMapper = userDTOMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public User createUser(UserSignUpDTO userSignUpDTO) {
        User user = new User(userSignUpDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    public User updateUserDetails(String username, Record dto) {
        // Récupérer l'utilisateur existant depuis la base
        User user = findUserByUsername(username);

        // Mettre à jour les informations en fonction du type de DTO
        user = updateUserFromDTO(user, dto);

        // Sauvegarder les modifications
        return userRepo.save(user);
    }

    private User updateUserFromDTO(User user, Record dto) {
        if (dto instanceof UserDTO userDTO) {
            // Mettre à jour les champs de UserDTO
            if (userDTO.firstName() != null)
                user.setFirstName(userDTO.firstName());
            if (userDTO.lastName() != null)
                user.setLastName(userDTO.lastName());
            if (userDTO.age() != null)
                user.setAge(userDTO.age());
            if (userDTO.email() != null)
                user.setEmail(userDTO.email());
            if (userDTO.phone() != null)
                user.setPhone(userDTO.phone());
            if (userDTO.addressDTO() != null) {
                user.setAddress(userDTO.addressDTO().address());
                user.setCity(userDTO.addressDTO().city());
                user.setZip(userDTO.addressDTO().zip());
            }

            // Mettre à jour les rôles
            if (userDTO.roles() != null) {
                Set<Role> newRoles = userDTO.roles().stream()
                        .map(this::findRoleByName)
                        .collect(Collectors.toSet());
                user.setRoles(newRoles);
            }
        } else if (dto instanceof UserProfileDTO userProfileDTO) {
            // Mettre à jour les champs de UserProfileDTO
            if (userProfileDTO.firstName() != null)
                user.setFirstName(userProfileDTO.firstName());
            if (userProfileDTO.lastName() != null)
                user.setLastName(userProfileDTO.lastName());
            if (userProfileDTO.age() != null)
                user.setAge(userProfileDTO.age());
            if (userProfileDTO.addressDTO() != null) {
                user.setAddress(userProfileDTO.addressDTO().address());
                user.setCity(userProfileDTO.addressDTO().city());
                user.setZip(userProfileDTO.addressDTO().zip());
            }
        } else {
            throw new UnsupportedOperationException("Unsupported Record type: " + dto.getClass().getName());
        }

        return user;
    }

    public User updateUserRoles(String username, Set<String> roleNames) {
        User user = findUserByUsername(username);
        Set<Role> newRoles = roleNames.stream().map(this::findRoleByName).collect(Collectors.toSet());

        user.getRoles().addAll(newRoles);
        return userRepo.save(user);

    }

    public User findUserByUsername(String username) {

        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found : " + email));
    }

    // public List<User> getUserByFirstNameAndLastName(String firstName, String
    // lastName) {
    // return userRepo.getUserByFirstNameAndLastName(firstName, lastName)
    // .orElseThrow(() -> new RuntimeException("User not found : " + email));
    // }

    public Role findRoleByName(String roleName) {
        return roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found : " + roleName));
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with [%s] not found !".formatted(id)));
    }

}
