package com.user_service.UserService.api.controllers;

import com.user_service.UserService.applications.models.User;
import com.user_service.UserService.applications.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/username")
    public User updateUsername(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newUsername = body.get("username");
        return userService.updateUsername(id, newUsername);
    }
    @PutMapping("/{id}/email")
    public User updateEmail(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newEmail = body.get("email");
        return userService.updateEmail(id, newEmail);
    }
    @PutMapping("/{id}/password")
    public User updatePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newPassword = body.get("password");
        return userService.updatePassword(id, newPassword);
    }
    @PutMapping("/{id}/firstName")
    public User updateFirstName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newFirstName = body.get("firstName");
        return userService.updateFirstName(id, newFirstName);
    }
    @PutMapping("/{id}/lastName")
    public User updateLastName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newLastName= body.get("lastName");
        return userService.updateLastName(id, newLastName);
    }
}
