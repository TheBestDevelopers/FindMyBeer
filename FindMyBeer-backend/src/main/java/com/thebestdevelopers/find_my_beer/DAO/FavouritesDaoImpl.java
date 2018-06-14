package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import com.thebestdevelopers.find_my_beer.repository.FavouritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jakub Pisula
 */
@Service
public class FavouritesDaoImpl implements FavouritesDao {

    @Autowired
    FavouritesRepository favRepository;

    @Override
    public BooleanDTO addFavourite(int userId, int pubId) {
        FavouritiesEntity favEntity = new FavouritiesEntity();
        favEntity.setClientId(userId);
        favEntity.setPubId(pubId);
        favRepository.save(favEntity);
        BooleanDTO fa = new BooleanDTO(true);
        return fa;
    }

    @Override
    public BooleanDTO deleteFavourite(int userId, int pubId) {
        List<FavouritiesEntity> favsEntitiesList = favRepository.findFavouritesEntityByClientId(userId);
        for(FavouritiesEntity favList : favsEntitiesList){
            if(favList.getPubId() == pubId) {
                favRepository.delete(favList);
                return new BooleanDTO(true);
            }
        }
        return new BooleanDTO(false);
    }
}