package dev.minitsonga.E_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.model.User;
import dev.minitsonga.E_shop.model.UserDTO;
import dev.minitsonga.E_shop.model.UserDTOMapper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserDTOMapper UserDTOMapper;

    public UserController(UserService userService, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.UserDTOMapper = userDTOMapper;
    }
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam String username) {
        return ResponseEntity.ok(userService.findAllUsers().stream().map(UserDTOMapper).collect(Collectors.toList()));
    }
    @PostMapping(path = "/add")
    public ResponseEntity<User> registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userSignUpDTO));
    }

    @PutMapping(path = "/{username}/roles/update")
    public ResponseEntity<User> updateUserRoles(@PathVariable String username, @RequestBody Set<String> newRolesName) {

        return ResponseEntity.ok(userService.updateUserRoles(username, newRolesName));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }



}
