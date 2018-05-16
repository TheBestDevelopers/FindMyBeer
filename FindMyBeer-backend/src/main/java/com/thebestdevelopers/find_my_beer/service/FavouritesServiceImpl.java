package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.FavouritesDao;
import com.thebestdevelopers.find_my_beer.DTO.FavouritiesDTO;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import com.thebestdevelopers.find_my_beer.repository.FavouritesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FavouritesServiceImpl implements FavouritesService {

    @Autowired
    FavouritesRepository favsRepository;

    @Autowired
    FavouritesDao favsDao;

    @Override
    public List<FavouritiesDTO> getFavourites(int clientId) {
        List<FavouritiesEntity> favouritesEntityList = favsRepository.findFavouritesEntityByClientId(clientId);
        List<FavouritiesDTO> favsList = new ArrayList<>();
        for(FavouritiesEntity favouritiesEntity : favouritesEntityList) {
            favsList.add(new FavouritiesDTO(favouritiesEntity.getPubId(), favouritiesEntity.getPubByPubId().getPubName()));
        }
        return favsList;
    }

    @Override
    public FavouritiesDTO addFavourite(int clientId, int pubId) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(favsDao.addFavourite(clientId, pubId), FavouritiesDTO.class);
    }
}
