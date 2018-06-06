package com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs;

/**
 *   @author Grzegorz Nowak
 */
public class GetPubsDTO {

    private Result[] results;

    public GetPubsDTO() {
    }

    public GetPubsDTO(Result[] results) {
        this.results = results;
    }

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }
}
