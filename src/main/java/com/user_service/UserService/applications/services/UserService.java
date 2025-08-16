package com.user_service.UserService.applications.services;

import com.user_service.UserService.applications.models.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO user, String password);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, String username, String email, String password, String firstName, String lastName);
}
