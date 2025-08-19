package com.user_service.UserService.api.resources.out_response;

import com.user_service.UserService.applications.enums.UserStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateCreated;
    private UserStatus status;
}
