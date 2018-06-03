package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.PubDTO;

import java.util.List;

/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 */
public interface PubService {
    PubDTO getPub(String username, String password);
    List<PubDTO> getAllPubs();
    PubDTO createPub(String pubName);
    Boolean changePubPassword(String username, String password, String newPassword);
    Boolean deletePub(String username, String password);
    PubDTO getPubInfo(int userId, int pubId);
}
