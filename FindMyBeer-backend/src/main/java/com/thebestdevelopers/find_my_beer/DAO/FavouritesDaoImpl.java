package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.model.ClientEntity;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import com.thebestdevelopers.find_my_beer.model.PubEntity;
import com.thebestdevelopers.find_my_beer.repository.ClientRepository;
import com.thebestdevelopers.find_my_beer.repository.FavouritesRepository;
import com.thebestdevelopers.find_my_beer.repository.PubRepository;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
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

    @Autowired
    PubRepository pubRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public BooleanDTO addFavourite(int userId, int pubId) {
        PubEntity pubEntity;
        pubEntity = pubRepository.findPubEntityByPubId(pubId);
        ClientEntity clientEntity = clientRepository.findClientEntityByClientId(userId);

        FavouritiesEntity favEntity = new FavouritiesEntity();
        favEntity.setClientId(userId);
        favEntity.setPubId(pubId);
        favEntity.setPubByPubId(pubEntity);
        favEntity.setClientByClientId(clientEntity);
        favRepository.save(favEntity);

        pubEntity.setFavouritiesByPubId(favRepository.findByPubId(pubId));
        pubRepository.save(pubEntity);

        clientEntity.setFavouritiesByClientId(favRepository.findFavouritesEntityByClientId(userId));
        clientRepository.save(clientEntity);

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