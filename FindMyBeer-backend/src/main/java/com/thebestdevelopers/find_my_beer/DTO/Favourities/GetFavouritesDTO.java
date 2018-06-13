package com.thebestdevelopers.find_my_beer.DTO.Favourities;

import java.util.List;

public class GetFavouritesDTO {
    private List<FavResult> results;

    public GetFavouritesDTO() {
    }

    public GetFavouritesDTO(List<FavResult> results) {
        this.results = results;
    }

    public List<FavResult> getFavResults() {
        return results;
    }

    public void setFavResults(List<FavResult> results) {
        this.results = results;
    }
}
