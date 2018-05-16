package com.thebestdevelopers.find_my_beer.DTO;


public class FavouritiesDTO{
    private int pubId;
    private String pubName;

    public FavouritiesDTO() {
    }

    public FavouritiesDTO(int pubId, String pubName) {
        this.pubId = pubId;
        this.pubName = pubName;
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
