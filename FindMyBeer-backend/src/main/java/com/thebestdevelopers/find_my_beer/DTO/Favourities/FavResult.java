package com.thebestdevelopers.find_my_beer.DTO.Favourities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jakub Pisula
 *
 */
public class FavResult {

    @JsonIgnore
    private Integer pubId;

    @JsonProperty("place_id")
    private Integer placeId;

    private String name;

    private String vicinity;

    public FavResult() {}

    public FavResult(Integer pubId, String name, String vicinity, Integer placeId) {
        this.pubId = pubId;
        this.name = name;
        this.vicinity = vicinity;
        this.placeId = placeId;
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

    public Integer getPlaceId() {
        return placeId;
    }
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
}
