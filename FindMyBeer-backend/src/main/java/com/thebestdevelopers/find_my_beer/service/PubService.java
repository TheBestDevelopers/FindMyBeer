package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.UserDTO;

import java.util.List;

/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 */
public interface PubService {
    UserDTO getPub(String username, String password);
    List<UserDTO> getAllPubs();
    UserDTO createPub(String username, String password, String role);
    Boolean changePubPassword(String username, String password, String newPassword);
    Boolean deletePub(String username, String password);
}
