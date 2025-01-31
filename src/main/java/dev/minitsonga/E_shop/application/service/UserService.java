package dev.minitsonga.E_shop.application.service;

import dev.minitsonga.E_shop.application.dto.Users.UserDTO;
import dev.minitsonga.E_shop.application.dto.Users.UserPasswordDTO;
import dev.minitsonga.E_shop.application.dto.Users.UserProfileDTO;
import dev.minitsonga.E_shop.application.dto.Users.UserSignUpDTO;
import dev.minitsonga.E_shop.domain.Role;
import dev.minitsonga.E_shop.domain.User;
import dev.minitsonga.E_shop.infrastructure.repo.RoleRepo;
import dev.minitsonga.E_shop.infrastructure.repo.UserRepo;
import jakarta.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo,
            JWTService jwtService, AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public User createUser(UserSignUpDTO userSignUpDTO) {

        User user = new User();
        user.setUsername(userSignUpDTO.username());
        user.setEmail(userSignUpDTO.email());
        user.setPassword(userSignUpDTO.password());
        Role defaultRole = new Role();
        defaultRole.setRole("USER");
        user.getRoles().add(defaultRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verifyUser(UserSignUpDTO usersSignUpDTO) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(usersSignUpDTO.username(), usersSignUpDTO.password()));

        if (authentication.isAuthenticated()) {
            User user = findUserByUsername(usersSignUpDTO.username());
            return jwtService.generateToken(user);
        }
        return null;
    }

    public User userExists(String username, String email) {

        if (userRepo.findUserByEmail(email).isPresent())
            return findUserByEmail(email);
        if (userRepo.findUserByUsername(username).isPresent())
            return findUserByUsername(username);

        return null;
    }

    public User updateUserDetails(Long id, Record dto) {
        User user = findUserById(id);
        user = updateUserFromDTO(user, dto);
        return userRepo.save(user);
    }

    private User updateUserFromDTO(User user, Record dto) {
        if (dto instanceof UserDTO userDTO) {
            // Mettre à jour les champs de UserDTO
            if (userDTO.username() != null)
                user.setUsername(userDTO.username());
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
            if (userProfileDTO.username() != null)
                user.setUsername(userProfileDTO.username());
            if (userProfileDTO.firstName() != null)
                user.setFirstName(userProfileDTO.firstName());
            if (userProfileDTO.lastName() != null)
                user.setLastName(userProfileDTO.lastName());
            if (userProfileDTO.age() != null)
                user.setAge(userProfileDTO.age());
            if (userProfileDTO.email() != null)
                user.setEmail(userProfileDTO.email());
            if (userProfileDTO.addressDTO() != null) {
                user.setAddress(userProfileDTO.addressDTO().address());
                user.setCity(userProfileDTO.addressDTO().city());
                user.setZip(userProfileDTO.addressDTO().zip());
            }
        } else if (dto instanceof UserPasswordDTO userPasswordDTO) {
            // Mettre à jour les champs de UserProfileDTO
            if (userPasswordDTO.newPassword() != null) {
                user.setPassword(userPasswordDTO.newPassword());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        } else {
            throw new UnsupportedOperationException("Unsupported Record type: " + dto.getClass().getName());
        }

        return user;
    }

    public User updateUserRoles(Long id, Set<String> roleNames) {
        User user = findUserById(id);
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
        return roleRepo.findByRole(roleName).orElseThrow(() -> new RuntimeException("Role not found : " + roleName));
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with [%s] not found !".formatted(id)));
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepo.deleteById(id);
    }

}
