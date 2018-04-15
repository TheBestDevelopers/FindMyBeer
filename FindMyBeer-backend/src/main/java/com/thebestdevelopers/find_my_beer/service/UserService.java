package com.thebestdevelopers.find_my_beer.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntity;

import java.util.List;

/**
 * @author Wiktor Florencki
 */
public interface UserService {
    UserDTO getUser(String username, String password);
    List<UserDTO> getAllUser();
    UserDTO createUser(String username, String password, String role);
    Boolean deleteUser(String username, String password);
}
