package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.PubDao;

import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubDTO;

import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.DTO.TablesDTO;
import com.thebestdevelopers.find_my_beer.model.*;
import com.thebestdevelopers.find_my_beer.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * @author Jakub Pisula
 */
@Service
public class PubServiceImpl implements PubService {

    @Autowired
    PubDao pubDao;

    @Autowired
    PubRepository pubRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ConvenienceRepository convenienceRepository;

    @Autowired
    ConvenienceTypeRepository convenienceTypeRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    TableDetailsRepository tableDetailsRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    FavouritesRepository favouritesRepository;

    @Override
    public PubDTO getPub(String username, String password) {
        return null;
    }

    @Override
    public List<PubDTO> getAllPubs() {
        return null;
    }

    @Override
    public PubDTO createPub(String pubName) {

        ModelMapper mapper = new ModelMapper();
        return mapper.map(pubDao.createPub(pubName), PubDTO.class);
    }

    @Override
    public Boolean changePubPassword(String username, String password, String newPassword) {
        return null;
    }

    @Override
    public Boolean deletePub(String username, String password) {
        return null;
    }

    private double countRatingsAverage(List<RatingsEntity> ratingsEntityList) {
        double ratingsValuesSum = 0;
        for(RatingsEntity ratingsEntity : ratingsEntityList){
            ratingsValuesSum += ratingsEntity.getRate();
        }

        return ratingsValuesSum/ratingsEntityList.size();
    }

    private boolean isFavourite(List<FavouritiesEntity> favouritiesEntityList, int clientId){
        boolean isFavouriteFlag = false;
        for(FavouritiesEntity favouritiesEntity : favouritiesEntityList){
            if(favouritiesEntity.getClientId()==clientId){
                isFavouriteFlag = true;
                break;
            }
        }

        return isFavouriteFlag;
    }

    @Override
    public PubInfoDTO getPubInfo(int userId, int pubId){
        PubEntity pubEntity;
        try {
            pubEntity = pubRepository.findByPubId(pubId).get(0);
        }catch(IndexOutOfBoundsException error){
            return new PubInfoDTO();
        }
        AddressesEntity addressesEntity = addressRepository.findByPubId(pubId).get(0);
        String address = addressesEntity.getStreet() + " " + addressesEntity.getNumber() + ", " + addressesEntity.getCity();

        List<ConveniencesEntity> conveniencesEntityList = convenienceRepository.findByPubId(pubId);
        Map<String, Boolean> convenienceMap = new TreeMap<>();
        List<ConvenienceTypesEntity> convenienceTypesEntityAll = convenienceTypeRepository.findAll();
        convenienceTypesEntityAll.forEach(convenienceType ->{convenienceMap.put(convenienceType.getDescription(), false);});
        for (ConveniencesEntity conveniencesEntity : conveniencesEntityList) {
            ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository.findByConvenienceTypesId(
                    conveniencesEntity.getConvenienceTypesId()).get(0);
            convenienceMap.replace(convenienceTypesEntity.getDescription(), true);
        }

        List<TablesEntity> tablesEntityList = tableRepository.findByPubId(pubId);
        int[] numberOfTables = new int[9]; //9 -> tables with 1 chair to 8 chairs
        for(TablesEntity tablesEntity : tablesEntityList){
            TableDetailsEntity tableDetailsEntity = tableDetailsRepository.findByTableId(tablesEntity.getTableId());
            numberOfTables[tableDetailsEntity.getPlaces()]++;
        }

        List<RatingsEntity> ratingsEntityList = ratingRepository.findByPubId(pubId);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        double ratingAverage = Double.parseDouble(df.format(this.countRatingsAverage(ratingsEntityList)));
        List<FavouritiesEntity> favouritiesEntityList = favouritesRepository.findByPubId(pubId);
        boolean isFavouriteFlag = this.isFavourite(favouritiesEntityList, userId);

        //parsing number of tables witch 1, 2, 4, 6, 8 chairs
        TablesDTO tablesDTO = new TablesDTO(numberOfTables[1],numberOfTables[2], numberOfTables[4], numberOfTables[6], numberOfTables[8]);

        PubInfoDTO pubInfoDTO = new PubInfoDTO(pubEntity.getPubName(), address, convenienceMap, tablesDTO,ratingAverage, isFavouriteFlag, true);

        return pubInfoDTO;
    }
}