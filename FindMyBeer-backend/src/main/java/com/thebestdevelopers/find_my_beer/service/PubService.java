package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.*;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;

import java.io.IOException;
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
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    PubInfoDTO getPubInfo(int userId, int pubId);
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    PubMenuDTO getPubMenu(int pubId);
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    GetPubsDTO getPubs(Double longitude, Double latitude) throws IOException;
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    List<GetNearestPubDTO> getNearestPubs(Double longitude, Double latitude) throws IOException;
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    List<GetNearestPubDTO> getPubsWithConveniences(Double longitude, Double latitude, String[] conveniences) throws IOException;
}
