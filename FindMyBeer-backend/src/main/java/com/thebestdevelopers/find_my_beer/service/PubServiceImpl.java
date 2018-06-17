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
 * Modyfications: Gzegorz Nowak
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
        double ratingAverage = 0;
        for(RatingsEntity ratingsEntity : ratingsEntityList){
            ratingsValuesSum += ratingsEntity.getRate();
        }
        ratingAverage = ratingsValuesSum/ratingsEntityList.size();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

        return Double.parseDouble(df.format(ratingAverage));
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
        List<FavouritiesEntity> favouritiesEntityList = favouritesRepository.findByPubId(pubId);
        boolean isFavouriteFlag = false;
        if (userId==-1)
            isFavouriteFlag = this.isFavourite(favouritiesEntityList, userId);

        //parsing number of tables witch 1, 2, 4, 6, 8 chairs
        TablesDTO tablesDTO = new TablesDTO(numberOfTables[1],numberOfTables[2], numberOfTables[4], numberOfTables[6], numberOfTables[8]);

        PubInfoDTO pubInfoDTO = new PubInfoDTO(pubEntity.getPubName(), address, convenienceMap, tablesDTO,
                this.countRatingsAverage(ratingsEntityList), isFavouriteFlag, true);

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
//        Map<String, Double> productsAndPrices = new TreeMap<>();
//        for(MenuEntity menuEntity : menuEntityList){
//            ProductsEntity productsEntity = productRepository.findByProdId(menuEntity.getProdId());
//            productsAndPrices.put(productsEntity.getDescription(), menuEntity.getPrice());
//        }

        String productsAndPrices = "";
        for(MenuEntity menuEntity : menuEntityList){
            ProductsEntity productsEntity = productRepository.findByProdId(menuEntity.getProdId());
            productsAndPrices += productsEntity.getDescription() + ":" + menuEntity.getPrice() + " ";
        }
        return new PubMenuDTO(productsAndPrices, menuEntityList.size());
    }

    @Override
    public GetPubsDTO getPubs(Double longitude, Double latitude) throws IOException {

        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);
        Result[] resultsArray = new Result[results.size()];
        return new GetPubsDTO(results.toArray(resultsArray));
    }

    private List<GetNearestPubDTO> getPubsWithTabes(List<Result> results){ //similar to getPubs byt returns other informations
        List<GetNearestPubDTO> getNearestPubDTOList = new ArrayList<>();
        Double averageRating;
        int numberOfFreeTables;
        for (Result result : results){
            averageRating = this.countRatingsAverage(ratingRepository.findByPubId(result.getPubId()));
            numberOfFreeTables = this.countFreeTables(tableRepository.findByPubId(result.getPubId()));
            Location location = result.getGeometry().getLocation();
            Double pubLongitude = Double.parseDouble(location.getLng());
            Double pubLatitude = Double.parseDouble(location.getLat());
            GetNearestPubDTO getNearestPubDTO = new GetNearestPubDTO(result.getPubId(), result.getName(), averageRating,
                    pubLongitude, pubLatitude, numberOfFreeTables, result.getDistance());
            getNearestPubDTOList.add(getNearestPubDTO);
        }
        return getNearestPubDTOList;
    }

    @Override
    public List<GetNearestPubDTO> getNearestPubs(Double longitude, Double latitude) throws IOException {
        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);

        return this.getPubsWithTabes(results);
    }

    Boolean isHaveConveniences(int pubId, String[] conveniences){

        List<ConveniencesEntity> conveniencesEntityList = convenienceRepository.findByPubId(pubId);
        boolean flag = false;
        for(String convenience : conveniences){
            flag = false;
            for(ConveniencesEntity conveniencesEntiy : conveniencesEntityList){
                ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository.findByConvenienceTypesId(conveniencesEntiy.getConvenienceTypesId()).get(0);
                if(convenience.equals(convenienceTypesEntity.getDescription())){
                    flag = true;
                    break;
                }
            }
            if(!flag)
                return false;
        }
        return true;
    }

    @Override
    public List<GetNearestPubDTO> getPubsWithConveniences(Double longitude, Double latitude, String[] conveniences) throws IOException {

        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);

        List<GetNearestPubDTO> getNearestPubDTOList = this.getPubsWithTabes(results);
        List<GetNearestPubDTO> getNearestPubWithConveniencesDTOList = new ArrayList<>();
        for (GetNearestPubDTO getNearestPubDTO : getNearestPubDTOList) {
            if(this.isHaveConveniences(getNearestPubDTO.getId(), conveniences)){
                getNearestPubWithConveniencesDTOList.add(getNearestPubDTO);
            }
        }

        return getNearestPubWithConveniencesDTOList;
    }
/*
    @Override
    public Boolean setConveniences(int pubId, String[] convToAdd, String[] convToDelete) {

        for(String conv : convToAdd){
            ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository.findByDescription(conv).get(0);
            ConveniencesEntity conveniencesEntity = new ConveniencesEntity();
            conveniencesEntity.setConvenienceTypesId(convenienceTypesEntity.getConvenienceTypesId());
            conveniencesEntity.setPubId(pubId);
            convenienceRepository.save(conveniencesEntity);
        }

        for (String conv : convToDelete){
            ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository.findByDescription(conv).get(0);
            ConveniencesEntity conveniencesEntity = convenienceRepository.findByConvenienceTypesIdAndPubId(convenienceTypesEntity.getConvenienceTypesId(), pubId).get(0);
            convenienceRepository.deleteById(conveniencesEntity.getConvenienceId());
        }

        return true;
    }
*/

    @Override
    public Boolean setConveniences(int pubId, String[] convenienceNames, Map<String, Boolean> convenienceNamesAndValues) {

        List<ConveniencesEntity> conveniencesEntityList =  convenienceRepository.findByPubId(pubId);

        convenienceRepository.deleteAll(conveniencesEntityList);

        for(String convenienceName : convenienceNames){
            if(convenienceNamesAndValues.get(convenienceName)){
                ConveniencesEntity conveniencesEntity = new ConveniencesEntity();
                ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository.findByDescription(convenienceName).get(0);
                conveniencesEntity.setConvenienceTypesId(convenienceTypesEntity.getConvenienceTypesId());
                conveniencesEntity.setPubId(pubId);
                convenienceRepository.save(conveniencesEntity);
                conveniencesEntity.setConvenienceTypesByConvenienceTypesId(convenienceTypesEntity);
                conveniencesEntity.setPubByPubId(pubRepository.findByPubId(pubId).get(0));
            }
        }
        return true;
    }

    @Override
    public BooleanDTO setTables(int pubId, int chair1, int chair2, int chair4, int chair6, int chair8) {
        for(TablesEntity tablesEntity : tableRepository.findByPubId(pubId)){
            tableDetailsRepository.delete(tableDetailsRepository.findByTableId(tablesEntity.getTableId()));
            tableRepository.delete(tablesEntity);
        }
        //chair 1
        for(int i = 0; i < chair1; i++){
            addTable(1, pubId);
        }
        //chair 2
        for(int i = 0; i < chair2; i++){
            addTable(2, pubId);
        }
        //chair4
        for(int i = 0; i < chair4; i++){
            addTable(4, pubId);
        }
        //chair 6
        for(int i = 0; i < chair6; i++){
            addTable(6, pubId);
        }
        //chair 8
        for(int i = 0; i < chair8; i++){
            addTable(8, pubId);
        }

        return new BooleanDTO(true);
    }

    private void addTable(int place, int pubId) {

        TablesEntity tablesEntity = new TablesEntity();
        tablesEntity.setPubId(pubId);
        //tablesEntity.setTableId(tableDetailsEntity.getTableId());
        tableRepository.save(tablesEntity);
        TableDetailsEntity tableDetailsEntity = new TableDetailsEntity();
        tableDetailsEntity.setOccupied(false);
        tableDetailsEntity.setPlaces(place);
        tableDetailsEntity.setTableId(tablesEntity.getTableId());
        tableDetailsEntity.setTablesByTableId(tablesEntity);
        tableDetailsRepository.save(tableDetailsEntity);

        tablesEntity.setTableDetailsByTableId(tableDetailsEntity);
        tableRepository.save(tablesEntity);
        PubEntity pubEntity = pubRepository.findPubEntityByPubId(pubId);
        pubEntity.setTablesByPubId(tableRepository.findByPubId(pubId));
        pubRepository.save(pubEntity);
    }
}