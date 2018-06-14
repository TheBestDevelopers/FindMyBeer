package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

/**
 * @author Jakub Pisula
 */
@Repository
public interface FavouritesDao {
    BooleanDTO addFavourite(int userId, int pubId);
    BooleanDTO deleteFavourite(int userId, int pubId);
}
