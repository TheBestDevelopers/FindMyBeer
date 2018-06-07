package com.thebestdevelopers.find_my_beer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebestdevelopers.find_my_beer.DAO.PubDao;

import com.thebestdevelopers.find_my_beer.DTO.*;

import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GoogleResponse;
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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

    private Boolean isPubNear(Double currentLongitude, Double currentLatitude, Double pubLongitude, Double pubLatitude, Double chosenDistance){

        final int earthRadius = 6371;

        double longitudeDistance = Math.toRadians(currentLongitude - pubLongitude);
        double latitudeDistance = Math.toRadians(currentLatitude - pubLatitude);
        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(pubLatitude))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double actualDistance = earthRadius * c * 1000; // convert to meters
        if(actualDistance<=chosenDistance)
            return true;
        else
            return false;
    }

    @Override
    public GetPubsDTO getPubs(Double longitude, Double latitude) throws IOException {

        final Double DISTANCE = 10000.0; //distance in meters

        final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
        List<AddressesEntity> addressesEntityList = addressRepository.findAll();
        GoogleResponse response;
        List<GoogleResponse> googleResponseList = new ArrayList<>();
        for(AddressesEntity addressesEntity : addressesEntityList){
            String fullAddress = addressesEntity.getStreet() + " " + addressesEntity.getNumber() + ", " + addressesEntity.getCity();
            URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8")
                    + "&sensor=false&key=AIzaSyDwKGwZZQdhb3_pzexvUyTX4oZy2MGrypg");
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream() ;
            ObjectMapper mapper = new ObjectMapper();
            response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
            googleResponseList.add(response);
            in.close();
        }

        int iter = 0;
        List<Result> results = new ArrayList<>();
        for(GoogleResponse googleResponse : googleResponseList) {
            if(googleResponse.getStatus().equals("OK")) {
                Location location = googleResponse.getResults()[0].getGeometry().getLocation();
                Double pubLongitude = Double.parseDouble(location.getLng());
                Double pubLatitude = Double.parseDouble(location.getLat());
                if (this.isPubNear(longitude, latitude, pubLongitude, pubLatitude, DISTANCE)) {
                    AddressesEntity currentAddress = addressesEntityList.get(iter);
                    String pubName = pubRepository.findByPubId(addressesEntityList.get(iter).getPubId()).get(0).getPubName();
                    String address = currentAddress.getStreet() + " " + currentAddress.getNumber() + ", " + currentAddress.getCity();
                    Result result = new Result(pubName, address, googleResponse.getResults()[0].getGeometry(),
                            ((Integer)addressesEntityList.get(iter).getPubId()).toString(), true);
                    results.add(result);
               }
            }
            iter++;
        }

        Result[] resultsArray = new Result[results.size()];
        return new GetPubsDTO(results.toArray(resultsArray));
    }
}