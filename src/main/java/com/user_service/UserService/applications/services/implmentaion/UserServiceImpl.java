package com.user_service.UserService.applications.services.implmentaion;

import com.user_service.UserService.applications.exceptions.InvalidUserDataException;
import com.user_service.UserService.applications.exceptions.UserNotFoundException;
import com.user_service.UserService.applications.models.User;
import com.user_service.UserService.applications.models.UserDTO;
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
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        if (user.getUserId() != null && userRepository.existsById(user.getUserId())) {
            throw new InvalidUserDataException("User ID already exists");
        }
        validateUser(user);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return convertToDTO(getUserOrThrow(id));
    }

    @Override
    public void deleteUser(Long id) {
        getUserOrThrow(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUser(Long id, String username, String email, String password, String firstName, String lastName) {
        User user = getUserOrThrow(id);
        if (username != null) {
            validateNotEmpty(username, "Username");
            checkUsernameAvailable(username);
            user.setUsername(username);
        }
        if (email != null) {
            validateNotEmpty(email, "Email");
            checkEmailAvailable(email);
            user.setEmail(email);
        }
        if (password != null) {
            validateNotEmpty(password, "Password");
             if (user.getPassword().equals(password)) {
            throw new InvalidUserDataException("New password cannot be the same as the current password.");
            }
            user.setPassword(password);
        }
        if (firstName != null) {
            validateNotEmpty(firstName, "First name");
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            validateNotEmpty(lastName, "Last name");
            user.setLastName(lastName);
        }
        return convertToDTO(userRepository.save(user));
    }

    // ---------- Helper Methods ----------

    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    // returns the user if exists else throws an exception
    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
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
