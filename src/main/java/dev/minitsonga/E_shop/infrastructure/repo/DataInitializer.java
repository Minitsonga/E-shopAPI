package dev.minitsonga.E_shop.infrastructure.repo;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.minitsonga.E_shop.domain.Role;
import dev.minitsonga.E_shop.domain.User;
import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        initializeRoles();
        initializeAdminUser();
    }

    public void initializeRoles() {
        if (!roleRepo.existsById("USER")) {
            Role userRole = new Role();
            userRole.setRole("USER");
            roleRepo.save(userRole);
        }

        if (!roleRepo.existsById("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleRepo.save(adminRole);
        }

        if (!roleRepo.existsById("VERIFIED")) {
            Role adminRole = new Role();
            adminRole.setRole("VERIFIED");
            roleRepo.save(adminRole);
        }

        if (!roleRepo.existsById("VIP")) {
            Role adminRole = new Role();
            adminRole.setRole("VIP");
            roleRepo.save(adminRole);
        }

    }

    private void initializeAdminUser() {
        // Vérifiez si un utilisateur admin existe déjà
        if (userRepo.findUserByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("AdminM3Min1ts0nga")); // Définir un mot de passe fort
            admin.setRoles(Set.of(
                    roleRepo.findByRole("ADMIN").orElseThrow(() -> new RuntimeException("Role ADMIN not found")),
                    roleRepo.findByRole("USER").orElseThrow(() -> new RuntimeException("Role USER not found"))));
            userRepo.save(admin);
        }
    }
}