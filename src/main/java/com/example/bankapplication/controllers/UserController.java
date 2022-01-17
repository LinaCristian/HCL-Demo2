package com.example.bankapplication.controllers;

import com.example.bankapplication.entities.User;
import com.example.bankapplication.models.PaggingModel;
import com.example.bankapplication.models.UserRegistrationModel;
import com.example.bankapplication.models.UserResponseModel;
import com.example.bankapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseModel saveUser(@RequestBody UserRegistrationModel user) throws ResponseStatusException {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<UserResponseModel> getAllUsers(PaggingModel pagging) {
        return userService.getAllUsers(pagging);
    }

    /*@DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/byName/{name}")
    public List<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/byFirstName/{firstName}")
    public List<User> getUserByFirstName(@PathVariable String firstName) {
        return userService.getUserByFirstName(firstName);
    }

    @PutMapping("{userId}")
    public User updateUserAge(@RequestBody User user, @PathVariable UUID userId) {
        return userService.updateUserAge(user, userId);
    }*/
}
