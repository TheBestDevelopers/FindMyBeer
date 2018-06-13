package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.GetFavouritesDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavouritiesDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface FavouritesService {
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    List<FavResult> getPubs(int userId) throws IOException;
    FavouritiesDTO addFavourite(int clientId, int pubId);
}
