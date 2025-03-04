package dev.minitsonga.E_shop.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.application.dto.Authentications.AuthenticationRequestDTO;
import dev.minitsonga.E_shop.application.dto.Authentications.AuthenticationResponseDTO;
import dev.minitsonga.E_shop.application.dto.Authentications.UserSignUpDTO;
import dev.minitsonga.E_shop.application.mapper.UserDTOMapper;
import dev.minitsonga.E_shop.application.service.JWTService;
import dev.minitsonga.E_shop.application.service.UserService;
import dev.minitsonga.E_shop.domain.RefreshToken;
import dev.minitsonga.E_shop.domain.User;
import dev.minitsonga.E_shop.infrastructure.exceptions.ApiException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService,
            UserDTOMapper userDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {

        if (userService.userExists(userSignUpDTO.username(), userSignUpDTO.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("Error", "User with this username or email already exists"));

        }
        User user = userService.createUser(userSignUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOMapper.apply(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequestDTO loginRequest) {

        User user = userService.userExists(loginRequest.usernameOrEmail(), loginRequest.usernameOrEmail());

        if (user != null) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.password()));

            if (authentication.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(user);
                RefreshToken refreshToken = jwtService.generateRefreshToken(user);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new AuthenticationResponseDTO(accessToken, refreshToken.getToken()));
            }
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Authentification", "Identifiants incorrects");
        }

        throw new ApiException(HttpStatus.UNAUTHORIZED, "Authentification", "Identifiants incorrects");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDTO> refreshAccessToken(
            @RequestBody AuthenticationResponseDTO request) {
        String refreshTokenString = request.refreshToken();
        RefreshToken refreshTokenOpt = jwtService.findRefreshToken(refreshTokenString);

        if (refreshTokenOpt != null) {
            User user = refreshTokenOpt.getUser();
            String newAccessToken = jwtService.generateAccessToken(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new AuthenticationResponseDTO(newAccessToken, refreshTokenOpt.getToken()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        User user = userService.userExists(request.get("username"), request.get("username"));
        jwtService.revokeRefreshToken(user);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}
