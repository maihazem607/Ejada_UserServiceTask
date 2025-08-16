package com.user_service.UserService.applications.services;

import com.user_service.UserService.applications.models.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    User updateUser(Long id, String username, String email, String password, String firstName, String lastName);
}
