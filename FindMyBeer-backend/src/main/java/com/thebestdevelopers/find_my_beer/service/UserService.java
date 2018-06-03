package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Dominik Florencki
 * The Shepa of adding authorities admin, client, pub
 */
public interface UserService {
    @PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    UserDTO getUser(String username);
    @PreAuthorize("hasAuthority('admin')")
    List<UserDTO> getAllUser();
    UserDTO createUser(String username, String password, String role);
    @PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    Boolean changeUserPassword(String username, String password, String newPassword);
    @PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    Boolean deleteUser(String username, String password);
}
