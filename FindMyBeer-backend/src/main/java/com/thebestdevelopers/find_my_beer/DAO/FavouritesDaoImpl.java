package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import com.thebestdevelopers.find_my_beer.repository.FavouritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouritesDaoImpl implements FavouritesDao {

    @Autowired
    FavouritesRepository favRepository;

    @Override
    public FavouritiesEntity addFavourite(int clientId, int pubId) {
        FavouritiesEntity favEntity = new FavouritiesEntity();
        favEntity.setClientId(clientId);
        favEntity.setPubId(pubId);
        favEntity = favRepository.save(favEntity);
        return favEntity;

    }
}