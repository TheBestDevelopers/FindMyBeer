package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.PubDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubMenuDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula, Grzegorz Nowak
 */
public interface PubService {
    PubDTO getPub(String username, String password);
    List<PubDTO> getAllPubs();
    PubDTO createPub(String pubName);
    Boolean changePubPassword(String username, String password, String newPassword);
    Boolean deletePub(String username, String password);
    @PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    PubInfoDTO getPubInfo(int userId, int pubId);
    @PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    PubMenuDTO getPubMenu(int pubId);
}
