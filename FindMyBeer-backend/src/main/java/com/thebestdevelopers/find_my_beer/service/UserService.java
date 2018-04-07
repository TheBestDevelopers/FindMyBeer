package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntity;

import java.util.List;

/**
 * @author Wiktor Florencki
 */
public interface UserService {
    List<UserDTO> getAllUser();
    UserDTO createUser(UserEntity user, String role);
}
