package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.FavouritiesDTO;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;

import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface FavouritesService {
    List<FavouritiesDTO> getFavourites(int clientId);
    FavouritiesDTO addFavourite(int clientId, int pubId);
}
