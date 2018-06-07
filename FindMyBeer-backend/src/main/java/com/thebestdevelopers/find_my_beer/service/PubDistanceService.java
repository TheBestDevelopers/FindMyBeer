package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Result;

import java.io.IOException;
import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
public interface PubDistanceService {

    Boolean isPubNear(Double currentLongitude, Double currentLatitude, Double pubLongitude, Double pubLatitude);
    Double getPubDistance(Double currentLongitude, Double currentLatitude, Double pubLongitude, Double pubLatitude);
    List<Result> getNearPubs(Double longitude, Double latitude) throws IOException;
}
