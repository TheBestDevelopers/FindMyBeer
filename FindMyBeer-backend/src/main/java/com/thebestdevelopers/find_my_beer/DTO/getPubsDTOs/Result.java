package com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   @author Grzegorz Nowak
 */
public class Result {

    @JsonIgnore
    private Integer pubId;

    @JsonIgnore
    private Double distance;

    private String name;

    private String vicinity;

    private Geometry geometry;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("our_pub")
    private Boolean ourPub;

    public Result() {
    }

    public Result(Integer pubId, Double distance, String name, String vicinity, Geometry geometry, String placeId, Boolean ourPub) {
        this.pubId = pubId;
        this.distance = distance;
        this.name = name;
        this.vicinity = vicinity;
        this.geometry = geometry;
        this.placeId = placeId;
        this.ourPub = ourPub;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getPubId() {
        return pubId;
    }

    public void setPubId(Integer pubId) {
        this.pubId = pubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Boolean getOurPub() {
        return ourPub;
    }

    public void setOurPub(Boolean ourPub) {
        this.ourPub = ourPub;
    }
}
