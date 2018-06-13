package com.thebestdevelopers.find_my_beer.DTO.Favourities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;

import java.util.List;


public class FavouritiesDTO{

    private int pubId;
    private String pubName;
    private String vicinity;


    public FavouritiesDTO() {
    }

    public FavouritiesDTO(List<FavouritiesEntity> favs) {

    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }
}
