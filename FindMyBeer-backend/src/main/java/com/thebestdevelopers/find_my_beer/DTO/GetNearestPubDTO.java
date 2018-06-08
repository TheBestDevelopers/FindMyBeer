package com.thebestdevelopers.find_my_beer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *   @author Grzegorz Nowak
 */
public class GetNearestPubDTO {

    private int id;

    private String name;

    private Double rating;

    @JsonIgnore
    private Double longitude;

    @JsonIgnore
    private Double latitude;

    private int freeTable;

    private Double distance;

    public GetNearestPubDTO(int pubId, String pubName, Double rating, Double longitude, Double latitude, int freeTable, Double distance) {
        this.id = pubId;
        this.name = pubName;
        this.rating = rating;
        this.longitude = longitude;
        this.latitude = latitude;
        this.freeTable = freeTable;
        this.distance = distance;
    }

    public GetNearestPubDTO() {
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getFreeTable() {
        return freeTable;
    }

    public void setFreeTable(int freeTable) {
        this.freeTable = freeTable;
    }
}
