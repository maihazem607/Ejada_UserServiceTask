package com.user_service.UserService.applications.services;

import com.user_service.UserService.api.resources.in_request.CreateUserRequest;
import com.user_service.UserService.api.resources.out_response.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(CreateUserRequest request);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, String username, String email, String password, String firstName, String lastName);
}
