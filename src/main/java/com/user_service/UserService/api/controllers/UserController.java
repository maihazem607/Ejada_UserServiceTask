package com.user_service.UserService.api.controllers;

import com.user_service.UserService.applications.models.UserDTO;
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
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }


    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newUsername = body.get("username");
        String newEmail = body.get("email");
        String newPassword = body.get("password");
        String newFirstName = body.get("firstName");
        String newLastName= body.get("lastName");
        return userService.updateUser(id, newUsername,newEmail, newPassword, newFirstName, newLastName);
    }
  
}
