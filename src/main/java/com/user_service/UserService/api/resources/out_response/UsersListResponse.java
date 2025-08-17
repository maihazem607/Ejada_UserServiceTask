package com.user_service.UserService.api.resources.out_response;

import com.user_service.UserService.applications.models.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersListResponse {

    private List<UserDTO> users;

}
