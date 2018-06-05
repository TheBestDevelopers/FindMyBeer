package com.thebestdevelopers.find_my_beer.DTO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;

/**
 *   @author Grzegorz Nowak
 */
@JsonTypeName("result")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
public class PubInfoDTO {

    private String name;
    private String vicinity;
    Map<String, Boolean> conveniences;
    TablesDTO tables;
    double rating;
    boolean favourite;
    boolean ourPub = true;

    public PubInfoDTO(){
        this.name = "404 not found";
        this.vicinity = "";
        this.conveniences = null;
        this.tables = null;
        this.rating = 0;
        this.favourite = false;
        this.ourPub = false;
    }

    public PubInfoDTO(String pubName, String vicinity, Map<String, Boolean> convenienceMap, TablesDTO tablesDTO, double rating, boolean favourite, boolean ourPub) {
        this.name = pubName;
        this.vicinity = vicinity;
        this.conveniences = convenienceMap;
        this.tables = tablesDTO;
        this.rating = rating;
        this.favourite = favourite;
        this.ourPub = ourPub;
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

    public Map<String, Boolean> getConveniences() {
        return conveniences;
    }

    public void setConveniences( Map<String, Boolean> conveniences) {
        this.conveniences = conveniences;
    }

    public TablesDTO getTables() {
        return tables;
    }

    public void setTables(TablesDTO tables) {
        this.tables = tables;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isOurPub() {
        return ourPub;
    }

    public void setOurPub(boolean ourPub) {
        this.ourPub = ourPub;
    }
}
