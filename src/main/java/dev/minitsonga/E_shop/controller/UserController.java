package dev.minitsonga.E_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.domain.User;
import dev.minitsonga.E_shop.application.dto.UserDTO;
import dev.minitsonga.E_shop.application.mapper.UserDTOMapper;
import dev.minitsonga.E_shop.application.dto.UserPasswordDTO;
import dev.minitsonga.E_shop.application.dto.UserSignUpDTO;
import dev.minitsonga.E_shop.application.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, UserDTOMapper userDTOMapper,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam String username) {
        List<UserDTO> users = userService.findAllUsers().stream()
                .map(userDTOMapper::apply)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        User user = userService.createUser(userSignUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOMapper.apply(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody Record dto) {
        User updatedUser = userService.updateUserDetails(id, dto);
        return ResponseEntity.ok(userDTOMapper.apply(updatedUser));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> updatePasswordUser(@PathVariable Long id,
            @RequestBody UserPasswordDTO userPasswordDTO) {
        User updatedUser = userService.updateUserDetails(id, userPasswordDTO);
        return ResponseEntity.ok(userDTOMapper.apply(updatedUser));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UserDTO> updateUserRoles(
            @PathVariable Long id,
            @RequestBody Set<String> roleNames) {
        User updatedUser = userService.updateUserRoles(id, roleNames);
        return ResponseEntity.ok(userDTOMapper.apply(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSignUpDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.username(), loginDTO.password()));
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
