package com.thebestdevelopers.find_my_beer.DTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
*   @author Grzegorz Nowak
*/
public class PubInfoDTO {

    private String pubName;
    private String vicinity;
    List<ConveniencesDTO> conveniencesDTOList;
    TablesDTO tablesDTO;
    double rating;
    boolean favourite;
    boolean ourPub = true;

    public PubInfoDTO(){

    }

    public PubInfoDTO(String pubName, String vicinity, List<ConveniencesDTO> conveniencesDTOList, TablesDTO tablesDTO, double rating, boolean favourite, boolean ourPub) {
        this.pubName = pubName;
        this.vicinity = vicinity;
        this.conveniencesDTOList = conveniencesDTOList;
        this.tablesDTO = tablesDTO;
        this.rating = rating;
        this.favourite = favourite;
        this.ourPub = ourPub;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public List<ConveniencesDTO> getConveniencesDTOList() {
        return conveniencesDTOList;
    }

    public void setConveniencesDTOList(List<ConveniencesDTO> conveniencesDTOList) {
        this.conveniencesDTOList = conveniencesDTOList;
    }

    public TablesDTO getTablesDTO() {
        return tablesDTO;
    }

    public void setTablesDTO(TablesDTO tablesDTO) {
        this.tablesDTO = tablesDTO;
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
