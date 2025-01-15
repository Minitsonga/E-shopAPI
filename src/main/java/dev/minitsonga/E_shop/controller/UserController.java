package dev.minitsonga.E_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.model.User;
import dev.minitsonga.E_shop.service.UserService;

import java.util.List;
import java.util.Set;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam String username) {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping(path = "/add")
    public ResponseEntity<User> registerUser(@RequestBody User user) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping(path = "/{username}/roles/update")
    public ResponseEntity<User> updateUserRoles(@PathVariable String username, @RequestBody Set<String> rolesName) {

        return ResponseEntity.ok(userService.updateUserRoles(username, rolesName));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }



}
