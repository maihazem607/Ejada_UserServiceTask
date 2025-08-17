package com.user_service.UserService.api.controllers;


import com.user_service.UserService.api.resources.in_request.CreateUserRequest;
import com.user_service.UserService.api.resources.out_response.UsersListResponse;
import com.user_service.UserService.applications.models.UserDTO;
import com.user_service.UserService.applications.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UsersListResponse> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        UsersListResponse response = new UsersListResponse(users);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(
            new UserDTO(null, request.getUsername(), request.getEmail(), request.getFirstName(), request.getLastName()),
            request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newUsername = body.get("username");
        String newEmail = body.get("email");
        String newPassword = body.get("password");
        String newFirstName = body.get("firstName");
        String newLastName = body.get("lastName");
        UserDTO updatedUser = userService.updateUser(id, newUsername, newEmail, newPassword, newFirstName, newLastName);
        return ResponseEntity.ok(updatedUser);
    }
  
}
