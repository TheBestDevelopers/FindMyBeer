package com.thebestdevelopers.find_my_beer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GoogleResponse;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Location;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Result;
import com.thebestdevelopers.find_my_beer.model.AddressesEntity;
import com.thebestdevelopers.find_my_beer.model.PubEntity;
import com.thebestdevelopers.find_my_beer.repository.AddressRepository;
import com.thebestdevelopers.find_my_beer.repository.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PubDistanceServiceImpl implements PubDistanceService {

    final Double DISTANCE = 10000.0; //distance in meters

    AddressRepository addressRepository;
    PubRepository pubRepository;

    public PubDistanceServiceImpl(AddressRepository addressRepository, PubRepository pubRepository) {
        this.addressRepository = addressRepository;
        this.pubRepository = pubRepository;
    }

    @Override
    public Double getPubDistance(Double currentLongitude, Double currentLatitude, Double pubLongitude, Double pubLatitude){

        final int earthRadius = 6371;

        double longitudeDistance = Math.toRadians(currentLongitude - pubLongitude);
        double latitudeDistance = Math.toRadians(currentLatitude - pubLatitude);
        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(pubLatitude))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double actualDistance = earthRadius * c * 1000; // convert to meters

        return actualDistance;
    }

    @Override
    public Boolean isPubNear(Double currentLongitude, Double currentLatitude, Double pubLongitude, Double pubLatitude){

        if(this.getPubDistance(currentLongitude, currentLatitude, pubLongitude, pubLatitude)<=DISTANCE)
            return true;
        else
            return false;
    }

    @Override
    public List<Result> getNearPubs(Double longitude, Double latitude) throws IOException {
        final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
        List<AddressesEntity> addressesEntityList = addressRepository.findAll();
        GoogleResponse response;
        List<GoogleResponse> googleResponseList = new ArrayList<>();
        for(AddressesEntity addressesEntity : addressesEntityList){
            String fullAddress = addressesEntity.getStreet() + " " + addressesEntity.getNumber() + ", " + addressesEntity.getCity();
            java.net.URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8")
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
                if (this.isPubNear(longitude, latitude, pubLongitude, pubLatitude)) {
                    AddressesEntity currentAddress = addressesEntityList.get(iter);
                    PubEntity pubEntity = pubRepository.findByPubId(addressesEntityList.get(iter).getPubId()).get(0);
                    String address = currentAddress.getStreet() + " " + currentAddress.getNumber() + ", " + currentAddress.getCity();
                    Result result = new Result(pubEntity.getPubId(), pubEntity.getPubName(), address, googleResponse.getResults()[0].getGeometry(),
                            ((Integer)addressesEntityList.get(iter).getPubId()).toString(), true);
                    results.add(result);
                }
            }
            iter++;
        }
        return results;
    }
}