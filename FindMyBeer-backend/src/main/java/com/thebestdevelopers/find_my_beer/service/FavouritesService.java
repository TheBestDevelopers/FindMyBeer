package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;

import java.io.IOException;
import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface FavouritesService {
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    List<FavResult> getFavouritePubs(int userId) throws IOException;
    BooleanDTO addFavourite(int userId, int pubId);
    BooleanDTO deleteFavourite(int userId, int pubId);
}
