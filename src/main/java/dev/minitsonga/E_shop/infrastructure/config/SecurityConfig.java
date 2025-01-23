package dev.minitsonga.E_shop.infrastructure.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuration des règles de sécurité HTTP
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Désactiver CSRF (à activer pour les formulaires)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/add").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN") // Restreindre l'accès aux admins
                        .requestMatchers("/api/products/**").authenticated() // Authentification requise
                        .anyRequest().permitAll() // Autoriser toutes les autres requêtes
                )
                .httpBasic(withDefaults()); // Utiliser HTTP Basic pour l'authentification (facile pour Postman)
        return http.build();
    }
}