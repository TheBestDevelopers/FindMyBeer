package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.FavouritesDao;
import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import com.thebestdevelopers.find_my_beer.repository.FavouritesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavouritesServiceImpl implements FavouritesService {

    @Autowired
    FavouritesRepository favsRepository;

    @Autowired
    FavouritesDao favsDao; //niby błąd ale działa XD

    @Override
    public List<FavResult> getPubs(int userId) throws IOException {
        List<FavouritiesEntity> favsEntitiesList = favsRepository.findFavouritesEntityByClientId(userId);
        List<FavResult> favResults = new ArrayList<>();
        String vicinity;

            for(FavouritiesEntity favList : favsEntitiesList){
                vicinity = favList.getPubByPubId().getAddressesByPubId().getStreet() + " " +
                        favList.getPubByPubId().getAddressesByPubId().getNumber() + ", " +
                        favList.getPubByPubId().getAddressesByPubId().getCity();
                FavResult favRes = new FavResult(favList.getPubId(), favList.getPubByPubId().getPubName(), vicinity, favList.getPubId());
                favResults.add(favRes);
        }
        return favResults;
    }

    @Override
    public BooleanDTO addFavourite(int userId, int pubId) {
        ModelMapper mapper = new ModelMapper();
        try {
            List<FavouritiesEntity> favsEntitiesList = favsRepository.findFavouritesEntityByClientId(userId);
            for(FavouritiesEntity favList : favsEntitiesList){
                if(favList.getPubId() == pubId)
                    return new BooleanDTO(false);
            }
            return favsDao.addFavourite(userId, pubId);

        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    @Override
    public BooleanDTO deleteFavourite(int userId, int pubId) {
       return favsDao.deleteFavourite(userId,pubId);
    }
}
