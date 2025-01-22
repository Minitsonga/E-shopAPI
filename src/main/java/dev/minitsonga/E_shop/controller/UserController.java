package dev.minitsonga.E_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.model.User;
import dev.minitsonga.E_shop.model.UserDTO;
import dev.minitsonga.E_shop.model.UserDTOMapper;
import dev.minitsonga.E_shop.model.UserPasswordDTO;
import dev.minitsonga.E_shop.model.UserSignUpDTO;
import dev.minitsonga.E_shop.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UserController(UserService userService, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
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
    public ResponseEntity<UserDTO> updatePasswordUser(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
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

}
