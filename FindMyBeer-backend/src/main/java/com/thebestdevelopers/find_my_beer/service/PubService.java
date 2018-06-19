package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.*;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasAnyAuthority('admin','client')")
    PubMenuDTO getPubMenu(int pubId);
    @PreAuthorize("hasAnyAuthority('admin','client')")
    GetPubsDTO getPubs(Double longitude, Double latitude) throws IOException;
    @PreAuthorize("hasAnyAuthority('admin','client')")
    List<GetNearestPubDTO> getNearestPubs(Double longitude, Double latitude) throws IOException;
    @PreAuthorize("hasAnyAuthority('admin','client')")
    List<GetNearestPubDTO> getPubsWithConveniences(Double longitude, Double latitude, String[] conveniences) throws IOException;
    @PreAuthorize("hasAnyAuthority('admin', 'pub')")
    Boolean setConveniences (int pubId, String[] convenienceNames, Map<String, Boolean> convenienceNamesAndValues);
    @PreAuthorize("hasAnyAuthority('admin', 'pub')")
    BooleanDTO setTables(int pubId, int chair1, int chair2, int chair4, int chair6, int chair8);
}