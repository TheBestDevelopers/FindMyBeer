package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface UserService {
    UserDTO getUser(String username, String password);
    @Secured({"user", "admin"})
    List<UserDTO> getAllUser();
    UserDTO createUser(String username, String password, String role);
    Boolean changeUserPassword(String username, String password, String newPassword);
    Boolean deleteUser(String username, String password);
}
