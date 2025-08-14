package com.user_service.UserService.applications.services.implmentaion;

import com.user_service.UserService.applications.exceptions.InvalidUserDataException;
import com.user_service.UserService.applications.exceptions.UserNotFoundException;
import com.user_service.UserService.applications.models.User;
import com.user_service.UserService.applications.repositories.UserRepository;
import com.user_service.UserService.applications.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if (user.getUserId() != null && userRepository.existsById(user.getUserId())) {
            throw new InvalidUserDataException("User ID already exists");
        }
        validateUser(user);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return getUserOrThrow(id);
    }

    @Override
    public void deleteUser(Long id) {
        getUserOrThrow(id);
        userRepository.deleteById(id);
    }

    @Override
    public User updateUsername(Long id, String username) {
        validateNotEmpty(username, "Username");
        checkUsernameAvailable(username);
        User user = getUserOrThrow(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public User updateEmail(Long id, String email) {
        validateNotEmpty(email, "Email");
        checkEmailAvailable(email);
        User user = getUserOrThrow(id);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(Long id, String password) {
        validateNotEmpty(password, "Password");
        User user = getUserOrThrow(id);
        if (user.getPassword().equals(password)) {
            throw new InvalidUserDataException("New password cannot be the same as the current password.");
        }
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User updateFirstName(Long id, String firstName) {
        validateNotEmpty(firstName, "First name");
        User user = getUserOrThrow(id);
        user.setFirstName(firstName);
        return userRepository.save(user);
    }

    @Override
    public User updateLastName(Long id, String lastName) {
        validateNotEmpty(lastName, "Last name");
        User user = getUserOrThrow(id);
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    // ---------- Helper Methods ----------


    // returns the user if exists else throws an exception
    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    private void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidUserDataException(fieldName + " cannot be empty");
        }
    }

    private void checkUsernameAvailable(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new InvalidUserDataException("Username already exists");
        }
    }

    private void checkEmailAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new InvalidUserDataException("Email already exists");
        }
    }

    private void validateUser(User user) {
        validateNotEmpty(user.getUsername(), "Username");
        validateNotEmpty(user.getEmail(), "Email");
        validateNotEmpty(user.getPassword(), "Password");
        validateNotEmpty(user.getFirstName(), "First name");
        validateNotEmpty(user.getLastName(), "Last name");
        checkUsernameAvailable(user.getUsername());
        checkEmailAvailable(user.getEmail());
    }
}
