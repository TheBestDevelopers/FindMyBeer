package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.PubDao;

import com.thebestdevelopers.find_my_beer.DTO.*;

import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Location;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Result;
import com.thebestdevelopers.find_my_beer.model.*;
import com.thebestdevelopers.find_my_beer.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import java.io.IOException;
import java.util.List;



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

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ProductRepository productRepository;

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

    private int countFreeTables(List<TablesEntity> tablesEntityList){
        int numberOfFreeTables = 0;
        for (TablesEntity tablesEntity : tablesEntityList){
            TableDetailsEntity tableDetailsEntity = tableDetailsRepository.findByTableId(tablesEntity.getTableId());
            if(!tableDetailsEntity.isOccupied())
                numberOfFreeTables++;
        }
        return numberOfFreeTables;
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

    @Override
    public PubMenuDTO getPubMenu(int pubId) {
        PubEntity pubEntity;
        try {
            pubEntity = pubRepository.findByPubId(pubId).get(0);
        }catch(IndexOutOfBoundsException error){
            return new PubMenuDTO();
        }

        List<MenuEntity> menuEntityList = menuRepository.findByPubId(pubEntity.getPubId());
        Map<String, Double> productsAndPrices = new TreeMap<>();
        for(MenuEntity menuEntity : menuEntityList){
            ProductsEntity productsEntity = productRepository.findByProdId(menuEntity.getProdId());
            productsAndPrices.put(productsEntity.getDescription(), menuEntity.getPrice());
        }

        return new PubMenuDTO(productsAndPrices);
    }

    @Override
    public GetPubsDTO getPubs(Double longitude, Double latitude) throws IOException {

        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);
        Result[] resultsArray = new Result[results.size()];
        return new GetPubsDTO(results.toArray(resultsArray));
    }

    @Override
    public List<GetNearestPubDTO> getNearestPubs(Double longitude, Double latitude) throws IOException {
        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);
        List<GetNearestPubDTO> getNearestPubDTOList = new ArrayList<>();
        Double averageRating;
        int numberOfFreeTables;
        for (Result result : results){
            averageRating = this.countRatingsAverage(ratingRepository.findByPubId(result.getPubId()));
            numberOfFreeTables = this.countFreeTables(tableRepository.findByPubId(result.getPubId()));
            Location location = result.getGeometry().getLocation();
            Double pubLongitude = Double.parseDouble(location.getLng());
            Double pubLatitude = Double.parseDouble(location.getLat());
            GetNearestPubDTO getNearestPubDTO = new GetNearestPubDTO(result.getPubId(), result.getName(), averageRating, pubLongitude, pubLatitude, numberOfFreeTables);
            getNearestPubDTOList.add(getNearestPubDTO);
        }
        return getNearestPubDTOList;
    }
}