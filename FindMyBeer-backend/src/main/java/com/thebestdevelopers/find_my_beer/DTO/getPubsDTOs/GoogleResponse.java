package com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   @author Grzegorz Nowak
 */
public class GoogleResponse {

    private ResultFromGoogle[] results ;

    private String status ;

    @JsonProperty("error_message")
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResultFromGoogle[] getResults() {
        return results;
    }
    public void setResults(ResultFromGoogle[] results) {
        this.results = results;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
