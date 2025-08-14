package com.user_service.UserService.applications.services;

import com.user_service.UserService.applications.models.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    User updateUsername(Long id, String username);
    User updateEmail(Long id, String email);
    User updatePassword(Long id, String password);
    User updateFirstName(Long id, String firstName);
    User updateLastName(Long id, String lastName);
}
