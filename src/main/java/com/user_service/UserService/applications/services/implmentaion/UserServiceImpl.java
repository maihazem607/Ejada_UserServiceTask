package com.user_service.UserService.applications.services.implmentaion;

import com.user_service.UserService.api.resources.in_request.CreateUserRequest;
import com.user_service.UserService.applications.enums.UserStatus;
import com.user_service.UserService.applications.exceptions.InvalidUserDataException;
import com.user_service.UserService.applications.exceptions.UserNotFoundException;
import com.user_service.UserService.applications.models.User;
import com.user_service.UserService.api.resources.out_response.UserDTO;
import com.user_service.UserService.applications.repositories.UserRepository;
import com.user_service.UserService.applications.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public UserDTO createUser(CreateUserRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateCreated(new Date());
        user.setStatus(UserStatus.ACTIVE);
        checkUsernameAvailable(request.getUsername());
        checkEmailAvailable(request.getEmail());
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
                user.getLastName(),
                user.getDateCreated(),
                user.getStatus()
        );
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

}
