package dev.minitsonga.E_shop.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.minitsonga.E_shop.domain.Role;
import dev.minitsonga.E_shop.domain.User;
import dev.minitsonga.E_shop.infrastructure.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepo userRepo;

        public CustomUserDetailsService(UserRepo userRepo) {
                this.userRepo = userRepo;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepo.findUserByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + username));
                System.out.println(user.getRoles().stream()
                                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                                "ROLE_" + role.getRole()))
                                .toList());
                return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                user.getRoles().stream()
                                                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                                                "ROLE_" + role.getRole()))
                                                .toList());
        }
}
