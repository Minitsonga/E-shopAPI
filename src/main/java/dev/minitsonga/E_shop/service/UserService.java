package dev.minitsonga.E_shop.service;

import dev.minitsonga.E_shop.config.SecurityConfig;
import dev.minitsonga.E_shop.model.Role;
import dev.minitsonga.E_shop.model.User;
import dev.minitsonga.E_shop.repo.RoleRepo;
import dev.minitsonga.E_shop.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
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

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public User createUser(User user) {
        Role defaultRole = getUserRoleByName("USER");
        user.getRoles().add(defaultRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);

    }

    public User updateUserRoles(String username, Set<String> roleNames) {
        User user = getUserByUsername(username);
        Set<Role> newRoles = roleNames.stream().map(this::getUserRoleByName).collect(Collectors.toSet());

        user.getRoles().addAll(newRoles);
        return userRepo.save(user);

    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found : " + username));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found : " + email));
    }

    public List<User> getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("User not found : " + firstName + " " + lastName));
    }

    public Role getUserRoleByName(String roleName) {
        return roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found : " + roleName));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
