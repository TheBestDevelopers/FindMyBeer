package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouritesDao {
    FavouritiesEntity addFavourite(int clientId, int pubId);
}
